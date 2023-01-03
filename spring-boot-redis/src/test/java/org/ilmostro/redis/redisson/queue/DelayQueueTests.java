package org.ilmostro.redis.redisson.queue;

import java.sql.Time;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RQueue;
import org.redisson.api.RedissonClient;
import org.redisson.codec.Kryo5Codec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author li.bowei
 */
@SpringBootTest
@Slf4j
public class DelayQueueTests {

	private static final String QUEUE_NAME = "test:delay:queue";

	@Autowired
	private RedissonClient redisson;

	@Test
	void test() throws Exception {
		final ExecutorService service = Executors.newFixedThreadPool(2);
		final RQueue<Integer> queue = redisson.getQueue(QUEUE_NAME, new Kryo5Codec());
		final RDelayedQueue<Integer> delayedQueue = redisson.getDelayedQueue(queue);
		service.submit(() -> {
			for (int i = 0; i < 10; i++) {
				delayedQueue.offer(i, i, TimeUnit.SECONDS);
			}
		});
		AtomicBoolean start = new AtomicBoolean(true);
		service.submit(() -> {
			while (start.get()) {
				final Integer poll = queue.poll();
				if (poll != null) {
					log.info("poll:[{}]", poll);
				}
			}
		});
		TimeUnit.SECONDS.sleep(10);
		start.compareAndSet(true, false);
	}
}
