package org.ilmostro.redis.redisson.limiter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author li.bowei
 */
@SpringBootTest
@Slf4j
public class SemaphoreTests {

	@Autowired
	private RedissonClient redisson;

	private static final String TEST_LIMITER_KEY = "test:semaphore:";


	@Test
	void test() throws Exception{

		final RSemaphore semaphore1 = redisson.getSemaphore(TEST_LIMITER_KEY + 1);
		final RSemaphore semaphore2 = redisson.getSemaphore(TEST_LIMITER_KEY + 2);
		final RSemaphore semaphore3 = redisson.getSemaphore(TEST_LIMITER_KEY + 3);

		final List<RSemaphore> semaphores = Arrays.asList(semaphore1, semaphore2, semaphore3);

		Map<RSemaphore, Integer> counter = new HashMap<>();
		counter.put(semaphore1, 5);
		counter.put(semaphore2, 3);
		counter.put(semaphore3, 2);



		for (int i = 0; i < 3; i++) {

		}
	}
}
