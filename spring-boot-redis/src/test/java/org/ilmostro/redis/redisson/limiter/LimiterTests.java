package org.ilmostro.redis.redisson.limiter;

import java.util.concurrent.CountDownLatch;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author li.bowei
 */
@SpringBootTest
@Slf4j
public class LimiterTests {

	@Autowired
	private RedissonClient redisson;

	private RRateLimiter limiter;

	private static final String TEST_LIMITER_KEY = "test:limiter";

	@BeforeEach
	void before(){
		limiter = redisson.getRateLimiter(TEST_LIMITER_KEY);
		// 三秒一个
		limiter.trySetRate(RateType.OVERALL, 1L, 3L, RateIntervalUnit.SECONDS);
	}

	@Test
	void test() throws Exception{
		final CountDownLatch latch = new CountDownLatch(10);
		for (int i = 0; i < 10; i++) {
			final int fi = i;
			limiter.acquireAsync().thenRun(() -> log.info("acquire! i:[{}]", fi)).thenRun(latch::countDown);
		}
		latch.await();
	}
}
