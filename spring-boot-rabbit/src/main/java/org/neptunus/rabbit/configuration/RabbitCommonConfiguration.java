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
        // 开启Returns模式
        factory.setPublisherReturns(true);
        /*
            * NONE值是禁用发布确认模式，是默认值
            * CORRELATED值是发布消息成功到交换器后会触发回调方法，就是触发template.setConfirmCallback()方法
            * SIMPLE值经测试有两种效果，其一效果和CORRELATED值一样会触发回调方法，
                其二在发布消息成功后使用rabbitTemplate调用waitForConfirms或waitForConfirmsOrDie方法等待broker节点返回发送结果，
                根据返回结果来判定下一步的逻辑，要注意的点是waitForConfirmsOrDie方法如果返回false则会关闭channel，则接下来无法发送消息到broker;
         */
        factory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
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
        /*
            RabbitMQ投递消息的方式为: Producer -> RabbitMQ Broker Cluster -> Exchange -> Queue -> Consumer
            confirmCallback的回调为 Producer -> RabbitMQ Broker Cluster过程的回调
            returnsCallback的回调为 RabbitMQ Broker Cluster -> Exchange过程的回调
         */
        template.setConfirmCallback((correlation, ack, cause) -> {
            if (ack) log.info("message:{} already ack!", correlation);
            else log.info("exchange not receive:{}, cause:{}", correlation, cause);
        });
        /*
            交换机接收到消息后可以判断当前的路径发送没有问题，但是不能保证消息能够发送到路由队列的。
            而发送者是不知道这个消息有没有送达队列的，因此，我们需要在队列中进行消息确认。这就是回退消息。
         */
        template.setReturnsCallback(returned -> log.info("exchange:{}, routing key:{}, message:{}, retry:{}",
                returned.getExchange(), returned.getRoutingKey(), returned.getMessage(), returned.getReplyCode()));
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
