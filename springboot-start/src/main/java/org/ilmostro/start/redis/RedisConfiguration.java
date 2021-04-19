package org.ilmostro.start.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * @author li.bowei
 */
@Configuration
@Slf4j
public class RedisConfiguration {

    @Bean
    public MessageListenerAdapter listenerAdapter() {
        return new MessageListenerAdapter(new CustomMessageListenerAdapter());
    }

    static class CustomMessageListenerAdapter{

        public void handleMessage(String message){
            log.info("get redis message:{}", message);
        }
    }

    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                                   MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic("test-topic"));
        return container;
    }
}
