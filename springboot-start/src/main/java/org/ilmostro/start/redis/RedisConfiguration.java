package org.ilmostro.start.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.BoundStreamOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;

import java.time.Duration;
import java.util.Iterator;
import java.util.UUID;

/**
 * @author li.bowei
 */
@Configuration
@Slf4j
public class RedisConfiguration {

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
                                                   MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic("task-topic"));
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
        return redisTemplate;
    }


    @Bean
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

}
