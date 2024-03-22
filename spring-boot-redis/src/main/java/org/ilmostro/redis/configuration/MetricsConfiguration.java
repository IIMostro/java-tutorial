package org.ilmostro.redis.configuration;

import io.micrometer.core.instrument.Tag;
import org.springframework.boot.actuate.metrics.cache.RedisCacheMetrics;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheManager;

import java.util.List;

@Configuration
public class MetricsConfiguration {
    @Bean
    public RedisCacheMetrics redisCacheMetrics(RedisCacheManager cacheManager) {
//        return new RedisCacheMetrics((RedisCache) cacheManager.getCache("magik"), List.of(Tag.of("application", "magik")));
    }
}
