package org.neptunus.rabbit.annotation;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerEndpoint;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.rabbit.listener.adapter.AbstractAdaptableMessageListener;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Component
@Aspect
@Slf4j
@Configuration
public class RabbitQueueAdapterSupport implements BeanDefinitionRegistryPostProcessor, BeanPostProcessor, RabbitListenerConfigurer, ApplicationContextAware {

    private BeanDefinitionRegistry registry;
    private ApplicationContext context;
    private static final String DEFAULT_EXCHANGE = "topic.method.adapter";
    private static final Map<Object, Method> QUEUE_ADAPTER_METHODS = new ConcurrentHashMap<>();
    private static final AtomicBoolean INIT = new AtomicBoolean(false);
    private static final ThreadLocal<String> INVOKER = new ThreadLocal<>();

    @Around("@annotation(adapter)")
    public Object around(ProceedingJoinPoint joinPoint, MethodQueueAdapter adapter) throws Throwable {
        final var object = new JSONObject();
        final var args = joinPoint.getArgs();
        final var method = (MethodSignature)joinPoint.getSignature();
        final var parameterNames = method.getParameterNames();
        for (int i = 0; i < args.length; i++) {
            object.put(parameterNames[i], args[i]);
        }
        if (StringUtils.isNotBlank(INVOKER.get())) {
            return joinPoint.proceed();
        }
        try{
            final var parameters = Arrays.stream(method.getParameterTypes()).map(Class::getSimpleName).collect(Collectors.joining("."));
            final var routingKey = AopUtils.getTargetClass(joinPoint.getThis()).getSimpleName() + "." + method.getName() + "." + parameters;
            context.getBean(RabbitTemplate.class).convertAndSend(DEFAULT_EXCHANGE, routingKey, object, message -> {
                message.getMessageProperties().setHeader("o-t-traceId", MDC.get("o-t-traceId"));
                return message;
            });
            return null;
        } catch (Exception ex){
            return joinPoint.proceed();
        }
    }

    @Override
    public Object postProcessAfterInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
        final var methods = MethodUtils.getMethodsListWithAnnotation(AopUtils.getTargetClass(bean), MethodQueueAdapter.class);
        if (CollectionUtils.isEmpty(methods)) {
            return bean;
        }
        for (Method method : methods) {
            QUEUE_ADAPTER_METHODS.put(bean, method);
            final var annotation = MethodUtils.getAnnotation(method, MethodQueueAdapter.class, true, true);
            final var parameters = Arrays.stream(method.getParameterTypes()).map(Class::getSimpleName).collect(Collectors.joining("."));
            initDirectQueue(annotation.queue(), AopUtils.getTargetClass(bean).getSimpleName() + "." + method.getName() + "." + parameters);
        }
        return bean;
    }

    private void initDirectQueue(String queueName, String routingKey) {
        final var queueDefinition = BeanDefinitionBuilder.genericBeanDefinition(Queue.class)
                .addConstructorArgValue(queueName)
                .addConstructorArgValue(true).getBeanDefinition();
        BeanDefinitionReaderUtils.registerWithGeneratedName(queueDefinition, registry);
        final var bindingDefinition = BeanDefinitionBuilder.genericBeanDefinition(Binding.class)
                .addConstructorArgValue(queueName)
                .addConstructorArgValue(Binding.DestinationType.QUEUE)
                .addConstructorArgValue(DEFAULT_EXCHANGE)
                .addConstructorArgValue(routingKey)
                .addConstructorArgValue(new HashMap<>())
                .getBeanDefinition();
        BeanDefinitionReaderUtils.registerWithGeneratedName(bindingDefinition, registry);
        if (INIT.compareAndSet(false, true)) {
            final var definition = BeanDefinitionBuilder.genericBeanDefinition(DirectExchange.class)
                    .addConstructorArgValue(DEFAULT_EXCHANGE)
                    .addConstructorArgValue(true)
                    .addConstructorArgValue(false)
                    .getBeanDefinition();
            BeanDefinitionReaderUtils.registerWithGeneratedName(definition, registry);
        }
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        QUEUE_ADAPTER_METHODS.forEach((bean, method) -> {
            final var entry = method.getAnnotation(MethodQueueAdapter.class);
            final var adapter = new DirectMessageHandlerAdapter(bean, method);
            final var endpoint = new SimpleRabbitListenerEndpoint();
            endpoint.setId("id." + entry.queue());
            endpoint.setQueueNames(entry.queue());
            endpoint.setMessageListener(adapter);
            registrar.registerEndpoint(endpoint);
        });
    }

    @Override
    public void postProcessBeanDefinitionRegistry(@NonNull BeanDefinitionRegistry registry) throws BeansException {
        this.registry = registry;
    }

    @Override
    public void postProcessBeanFactory(@NonNull ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // ignore
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    static final class DirectMessageHandlerAdapter extends AbstractAdaptableMessageListener {

        private final Object target;
        private final Method method;

        DirectMessageHandlerAdapter(Object target, Method method) {
            this.target = target;
            this.method = method;
        }

        @Override
        public void onMessage(Message message, Channel channel) throws Exception {
            final var body = new String(message.getBody(), StandardCharsets.UTF_8);
            final var tid = Optional.of(message)
                    .map(Message::getMessageProperties)
                    .map(v1 -> v1.getHeader("o-t-traceId"))
                    .map(Object::toString)
                    .orElseGet(() -> UUID.randomUUID().toString());
            log.info("message receive tid:[{}]", tid);
            MDC.put("o-t-traceId", tid);
            final var object = JSON.parseObject(body);
            final var parameters = method.getParameters();
            final var args = new Object[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                final var parameter = parameters[i];
                final var name = parameter.getName();
                final var type = parameter.getType();
                final var value = object.getObject(name, type);
                args[i] = value;
            }
            try {
                INVOKER.set(Optional.of(message).map(Message::getMessageProperties).map(MessageProperties::getMessageId).map(Object::toString).orElse("unknown"));
                MethodUtils.invokeMethod(target, method.getName(), args);
            } catch (Exception e) {
                log.error("invoke method error", e);
                throw new RuntimeException(e);
            } finally {
                INVOKER.remove();
            }
        }
    }
}
