package org.ilmostro.redis.service;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author li.bowei
 */
@Service
public class CacheBreakdownService {

	private static final String CACHE_PREFIX = "cache:breakdown:value:";

	private final RSemaphore semaphore;
	private final StringRedisTemplate redis;

	public CacheBreakdownService(RedissonClient redisson,
			StringRedisTemplate redis) {
		this.semaphore = redisson.getSemaphore("cache:breakdown:semaphore");
		this.redis = redis;
	}

	public String getCache(int key){
		String value = redis.opsForValue().get(CACHE_PREFIX + key);
		if(StringUtils.isNotBlank(value)) return value;
		if (semaphore.tryAcquire()) {
			//get value from database;
			redis.opsForValue().set(CACHE_PREFIX + key, CACHE_PREFIX + key, 10, TimeUnit.MINUTES);
			return CACHE_PREFIX + key;
		}
		else return getCache(key);
	}
}
