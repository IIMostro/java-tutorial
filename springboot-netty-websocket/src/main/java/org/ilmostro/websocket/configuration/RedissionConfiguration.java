package org.ilmostro.websocket.configuration;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class RedissionConfiguration {

    @Bean
    public RedissonClient redissonClient(RedisProperties properties){
        Config config = new Config();
        RedisProperties.Pool poolProperties = Optional.of(properties)
                .map(RedisProperties::getJedis)
                .map(RedisProperties.Jedis::getPool)
                .orElse(null);
        Integer maximumIdleSize = Optional.ofNullable(poolProperties)
                .map(RedisProperties.Pool::getMaxIdle)
                .orElse(20);

        Integer minimumIdleSize = Optional.ofNullable(poolProperties)
                .map(RedisProperties.Pool::getMinIdle)
                .orElse(5);
        config.useSingleServer()
                .setAddress("redis://" + properties.getHost() + ":" + properties.getPort())
                .setDatabase(properties.getDatabase())
                .setConnectionPoolSize(maximumIdleSize)
                .setConnectionMinimumIdleSize(minimumIdleSize);
        return Redisson.create(config);

    }

}
