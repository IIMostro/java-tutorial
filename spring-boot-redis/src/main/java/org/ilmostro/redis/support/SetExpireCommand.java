package org.ilmostro.redis.support;

import java.util.Objects;

import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author li.bowei
 */
public class SetExpireCommand implements ExpireCommand{

	private final StringRedisTemplate redis;

	public SetExpireCommand(StringRedisTemplate redis) {
		this.redis = redis;
	}

	@Override
	public boolean execute(String key, Object value) {
		final Long remove = redis.opsForSet().remove(key, value);
		return Objects.nonNull(remove) && remove >= 1;
	}
}
