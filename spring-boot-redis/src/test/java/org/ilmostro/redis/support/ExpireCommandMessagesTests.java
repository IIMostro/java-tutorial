package org.ilmostro.redis.support;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

/**
 * @author li.bowei
 */
class ExpireCommandMessagesTests {

	@Test
	void map(){
		final ExpireCommandMessage message = ExpireCommandMessages.map().key("").value("").expire(1L)
				.unit(TimeUnit.SECONDS).build();


	}
}