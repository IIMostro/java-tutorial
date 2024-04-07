package org.ilmostro.redis.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CachingService {

    @Cacheable(value = "example", key = "#bucket.concat(':').concat(#key)")
    public String get(String bucket, String key){
        return "Hello World";
    }

    @CachePut(value = "example", key = "#bucket.concat(':').concat(#key)")
    public String set(String bucket, String key, String value){
        return value;
    }

    @CacheEvict(value = "example", key = "#bucket.concat(':').concat(#key)")
    public void delete(String bucket, String key){
        // do nothing
    }
}
