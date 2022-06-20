package org.ilmostro.redis.support;

import java.util.Objects;

import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author li.bowei
 */
public class MapExpireCommand implements ExpireCommand {

	private final StringRedisTemplate redis;

	public MapExpireCommand(StringRedisTemplate redis) {
		this.redis = redis;
	}

	@Override
	public boolean execute(String key, Object value) {
		final Long delete = redis.opsForHash().delete(key, value);
		return delete >= 1;
	}
}
