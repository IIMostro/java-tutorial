package org.ilmostro.redis.configuration;

import io.micrometer.core.instrument.Tag;
import org.springframework.boot.actuate.metrics.cache.RedisCacheMetrics;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheManager;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

//@Configuration
public class MetricsConfiguration {
    @Bean
    public List<RedisCacheMetrics> redisCacheMetrics(RedisCacheManager cacheManager, CacheProperties properties, ApplicationContext context) {
        return Optional.of(properties)
                .map(CacheProperties::getCacheNames)
                .orElseGet(Collections::emptyList)
                .stream()
                .map(cacheManager::getCache)
                .filter(cache -> cache instanceof RedisCache)
                .map(cache -> new RedisCacheMetrics((RedisCache) cache, List.of(Tag.of("application", context.getApplicationName()))))
                .toList();
    }
}
