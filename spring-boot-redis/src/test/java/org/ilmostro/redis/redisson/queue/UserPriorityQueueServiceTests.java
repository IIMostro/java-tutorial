package org.ilmostro.redis.redisson.queue;

import lombok.extern.slf4j.Slf4j;
import org.ilmostro.redis.domain.User;
import org.ilmostro.redis.utils.ObjectMapperUtils;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author li.bowei
 */
@SpringBootTest
@Slf4j
class UserPriorityQueueServiceTests {

	@Autowired
	private UserPriorityQueueService service;

	@Test
	void add() {
		for (int i = 0; i < 20; i++) {
			final User supplier = User.supplier(false);
			log.info("user:[{}]", ObjectMapperUtils.toJSONString(supplier));
			service.add(supplier);
		}
	}
}