package org.neptunus.rabbit.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.time.Duration;
import java.util.Optional;

/**
 * @author li.bowei
 */
@Configuration
@Slf4j
public class RabbitCommonConfiguration {

    @Bean
    public ConnectionFactory connectionFactory(RabbitProperties properties){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(properties.getHost());
        factory.setPort(properties.getPort());
        factory.setUsername(properties.getUsername());
        factory.setPassword(properties.getPassword());
        factory.setConnectionTimeout(1000);
        factory.setVirtualHost(properties.getVirtualHost());
        return factory;
    }

    @Bean
    public CachingConnectionFactory rabbitConnectionFactory(ConnectionFactory connectionFactory){
        CachingConnectionFactory factory = new CachingConnectionFactory(connectionFactory);
        factory.setChannelCacheSize(25);
//        factory.setExecutor();
        return factory;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(CachingConnectionFactory rabbitConnectionFactory,
                                                                               RetryTemplate retryTemplate,
                                                                               ObjectMapper objectMapper) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(rabbitConnectionFactory);
        // 并发消费者数量
        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(20);
        // 手动提交
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        // 序列化方式
        factory.setMessageConverter(new Jackson2JsonMessageConverter(objectMapper));
        factory.setAdviceChain(RetryInterceptorBuilder.stateless()
                .recoverer(new RejectAndDontRequeueRecoverer())
                .retryOperations(retryTemplate).build());
        return factory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory rabbitConnectionFactory,
                                         RetryTemplate retryTemplate,
                                         ObjectMapper objectMapper) {
        RabbitTemplate template = new RabbitTemplate(rabbitConnectionFactory);
        // 序列化
        template.setMessageConverter(new Jackson2JsonMessageConverter(objectMapper));
        // 需要开启这个模式才能触发回调函数，无论推送结果怎么样
        template.setMandatory(true);
        template.setRetryTemplate(retryTemplate);
        template.setConfirmCallback((correlation, ack, cause) -> log.info("confirm data:{}, ack:{}, cause:{}", correlation, ack, cause));
        template.setReturnsCallback(returned -> log.info("exchange:{}, routing key:{}, message:{}, retry:{}", returned.getExchange(), returned.getRoutingKey(), returned.getMessage(), returned.getReplyCode()));
        return template;
    }

    @Bean
    public RetryTemplate retryTemplate(ExponentialBackOffPolicy backOffPolicy,
                                       RetryPolicy retryPolicy) {
        RetryTemplate template = new RetryTemplate();
        // 设置监听 调用重试处理过程
        template.registerListener(new RetryListener() {
            @Override
            public <T, E extends Throwable> boolean open(RetryContext context, RetryCallback<T, E> callback) {
                // 执行之前调用 返回false会终止执行
                return true;
            }

            @Override
            public <T, E extends Throwable> void close(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
                // 重试结束的时候调用（最后一次重试）
                log.debug("this is rabbitmq retry last execute!");
            }

            @Override
            public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
                // 异常都会调用
                log.debug("current: {} execute", context.getRetryCount());
            }
        });
        template.setBackOffPolicy(backOffPolicy);
        template.setRetryPolicy(retryPolicy);
        return template;
    }

    @Bean
    public ExponentialBackOffPolicy backOffPolicy(RabbitProperties properties) {
        ExponentialBackOffPolicy policy = new ExponentialBackOffPolicy();
        Optional.of(properties)
                .map(RabbitProperties::getListener)
                .map(RabbitProperties.Listener::getSimple)
                .map(RabbitProperties.AmqpContainer::getRetry)
                .map(RabbitProperties.Retry::getMaxInterval)
                .map(Duration::getSeconds)
                .map(v1 -> v1 * 1000)
                .ifPresent(policy::setMaxInterval);

        Optional.of(properties)
                .map(RabbitProperties::getListener)
                .map(RabbitProperties.Listener::getSimple)
                .map(RabbitProperties.AmqpContainer::getRetry)
                .map(RabbitProperties.Retry::getInitialInterval)
                .map(Duration::getSeconds)
                .map(v1 -> v1 * 1000)
                .ifPresent(policy::setInitialInterval);

        Optional.of(properties)
                .map(RabbitProperties::getListener)
                .map(RabbitProperties.Listener::getSimple)
                .map(RabbitProperties.AmqpContainer::getRetry)
                .map(RabbitProperties.Retry::getMultiplier)
                .ifPresent(policy::setMultiplier);

        return policy;
    }

    @Bean
    public RetryPolicy retryPolicy(RabbitProperties properties) {
        return Optional.of(properties)
                .map(RabbitProperties::getListener)
                .map(RabbitProperties.Listener::getSimple)
                .map(RabbitProperties.AmqpContainer::getRetry)
                .map(RabbitProperties.Retry::getMaxAttempts)
                .map(SimpleRetryPolicy::new)
                .orElseThrow();
    }
}
