package org.ilmostro.user.utils;

import org.ilmostro.user.domain.BasicEntity;
import org.ilmostro.user.domain.properties.BasicRedisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author IlMostro
 * @date 2019-11-05 23:28
 */
@Component
public class CacheUtils<T extends BasicEntity> {

    private final RedisTemplate<String, ? super BasicEntity> template;

    @Autowired
    public CacheUtils(@Qualifier("redisTemplate") RedisTemplate template) {
        //noinspection unchecked
        this.template = template;
    }

    public  T get(BasicRedisProperties properties, String key){
        //noinspection unchecked
        return (T) template.opsForValue().get(properties.getKey()+key);
    }

    public void set(BasicRedisProperties properties, T data){
        template.opsForValue().set(properties.getKey() + data.getId(), data, properties.getTimeout(), TimeUnit.SECONDS);
    }
}
