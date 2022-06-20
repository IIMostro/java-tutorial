package org.ilmostro.redis.support;

/**
 * @author li.bowei
 */
public interface ExpireCommand {

	boolean execute(String key, Object value);
}
