package org.ilmostro.redis.structure;

import java.util.concurrent.TimeUnit;

import org.ilmostro.redis.redisson.queue.BlockingQueueService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author li.bowei
 */
@SpringBootTest
public class BlockingQueueServiceTests {

	@Autowired
	private BlockingQueueService service;

	@Test
	void test() {
		for (int i = 0; i < 5; i++) {
			service.offer(String.valueOf(i), 1 << i, TimeUnit.SECONDS);
		}
	}
}
