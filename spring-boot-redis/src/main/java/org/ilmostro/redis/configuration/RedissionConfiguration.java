package org.ilmostro.redis.configuration;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.ilmostro.redis.listener.MessageListenerAdapterWrapper;
import org.ilmostro.redis.listener.ProductUpdateStreamListener;
import org.redisson.Redisson;
import org.redisson.config.Config;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamInfo;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;

/**
 * @author li.bowei
 */
@Configuration
@Slf4j
public class RedissionConfiguration {

    @Bean
    public Redisson redissonClient(RedisProperties properties) {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://" + properties.getHost() + ":" + properties.getPort())
                .setPassword(properties.getPassword());
        return (Redisson) Redisson.create(config);
    }

    public static final String REDIS_STREAM_GROUP = "redis_stream_group";
    public static final String REDIS_STREAM_NAME = "redis_stream";

    @Bean
    public MessageListenerAdapter listenerAdapter() {
        return new MessageListenerAdapter(new CustomMessageListenerAdapter());
    }

    static class CustomMessageListenerAdapter {
        public void handleMessage(String message) {
            log.info("get redis message:{}", message);
        }
    }

    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                                   MessageListenerAdapter listenerAdapter,
                                                   //这个wrappers可以在bean里面初始化也可以使用下面的方法动态的去注册bean
                                                   List<MessageListenerAdapterWrapper> wrappers) {
        /*
         动态添加wrappers
         private void registerMessageListenerAdapter () {
         BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(MessageListenerAdapterWrapper.class, () -> {
         MessageListenerAdapterWrapper wrapper = new MessageListenerAdapterWrapper(this);
         wrapper.setTopic(topic.getTopicStringName());
         return wrapper;
         });
         BeanDefinition beanDefinition = builder.getRawBeanDefinition();
         ((DefaultListableBeanFactory) context.getAutowireCapableBeanFactory())                 .
            registerBeanDefinition("messageListenerAdapterWrapper" + topic.name().toLowerCase(), beanDefinition);
         }
         */
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic("task-topic"));
        for (MessageListenerAdapterWrapper wrapper : wrappers) {
            container.addMessageListener(wrapper, new PatternTopic(wrapper.getTopic()));
        }
        return container;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory,
                                                       ObjectMapper objectMapper) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(connectionFactory);
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer =
                new Jackson2JsonRedisSerializer<>(Object.class);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        //设置开启事务支持
        redisTemplate.setEnableTransactionSupport(true);
        return redisTemplate;
    }


    //    @Bean
    public StreamMessageListenerContainer<String, MapRecord<String, String, String>> streamMessageListenerContainer(RedisConnectionFactory connectionFactory,
                                                                                                                    RedisTemplate<String, Object> redisTemplate) {
        initConsumer(redisTemplate);
        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, MapRecord<String, String, String>> containerOptions =
                StreamMessageListenerContainer.StreamMessageListenerContainerOptions
                        .builder()
                        // 一次最多拉取5条消息
                        .batchSize(5)
                        .errorHandler(ex -> log.error("redis listener error, message:{}, cause:{}", ex.getMessage(), ex.getCause()))
                        // 拉取消息的超时时间
                        .pollTimeout(Duration.ZERO)
                        .build();
        // 流消息订阅者容器
        StreamMessageListenerContainer<String, MapRecord<String, String, String>> streamMessageListenerContainer =
                StreamMessageListenerContainer.create(connectionFactory, containerOptions);
        streamMessageListenerContainer.receive(Consumer.from(REDIS_STREAM_GROUP, UUID.randomUUID().toString()),
                //从最后开始消费
                StreamOffset.create(REDIS_STREAM_NAME, ReadOffset.lastConsumed()),
                new ProductUpdateStreamListener(redisTemplate));
        streamMessageListenerContainer.start();
        return streamMessageListenerContainer;
    }

    /**
     * 初始化stream和消费组
     *
     * @param redisTemplate redisTemplate
     */
    private void initConsumer(RedisTemplate<String, Object> redisTemplate) {
        redisTemplate.boundStreamOps(REDIS_STREAM_NAME);
        StreamInfo.XInfoGroups groups = redisTemplate.opsForStream().groups(REDIS_STREAM_NAME);
        Iterator<StreamInfo.XInfoGroup> iterator = groups.iterator();
        if(iterator.hasNext()){
            StreamInfo.XInfoGroup next = iterator.next();
            if(next.groupName().equals(REDIS_STREAM_GROUP)){
                return;
            }
        }
        redisTemplate.opsForStream().createGroup(REDIS_STREAM_NAME, REDIS_STREAM_GROUP);
    }

//    @Bean
//    public RedissonConnectionFactory redissonConnectionFactory(RedissonClient redisson) {
//        return new RedissonConnectionFactory(redisson);
//    }
}
