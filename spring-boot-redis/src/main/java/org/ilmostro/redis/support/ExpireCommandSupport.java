package org.ilmostro.redis.support;

/**
 * @author li.bowei
 */
public interface ExpireCommandSupport {

	boolean delay(ExpireCommandMessage message);

	boolean process(ExpireCommandMessage message);
}
