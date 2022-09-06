package org.ilmostro.redis.redisson.structure;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RPriorityBlockingQueue;
import org.redisson.api.RPriorityQueue;
import org.redisson.api.RQueue;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import reactor.core.scheduler.Schedulers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author li.bowei
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class PriorityQueueTests {

	@Autowired
	private RedissonClient redisson;

	@Test
	public void test() {
		final RPriorityQueue<Ranking> queue = redisson.getPriorityQueue("priority_queue_tuple_test", JsonJacksonCodec.INSTANCE);
		queue.trySetComparator(new Ranking());
		List<Ranking> list = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			list.add(new Ranking(i, i));
		}
		queue.addAll(list);
		final Ranking rank = queue.poll();
		log.info("{}", rank);
	}

	@Test
	public void queue() throws Exception{
		String name = "r_priority_blocking_queue";
		final RQueue<String> queue = redisson.getQueue(name);
		final RDelayedQueue<String> delayedQueue = redisson.getDelayedQueue(queue);
		Schedulers.single().schedulePeriodically(new RedissonTask(delayedQueue),0, 1, TimeUnit.SECONDS);
		for (int i = 0; i < 10; i++) {
			delayedQueue.offer(String.valueOf(i), i, TimeUnit.SECONDS);
		}
		TimeUnit.MINUTES.sleep(1);
	}

	@Test
	public void delay_queue() throws Exception{
		String name = "delay_queue_name";
		final RQueue<String> queue = redisson.getQueue(name);
		final RDelayedQueue<String> delayedQueue = redisson.getDelayedQueue(queue);
		Schedulers.single().schedulePeriodically(new RedissonTask(delayedQueue), 0, 1, TimeUnit.SECONDS);
		for (int i = 0; i < 10; i++) {
			delayedQueue.offer(String.valueOf(i), i, TimeUnit.SECONDS);
		}
		TimeUnit.MINUTES.sleep(1);
	}

	private static class RedissonTask implements Runnable{

		private final RDelayedQueue<String> queue;

		private RedissonTask(RDelayedQueue<String> queue) {
			this.queue = queue;
		}

		@Override
		public void run() {
			while(Objects.nonNull(queue.peek())){
				final String take = queue.poll();
				if (StringUtils.isNotBlank(take)){
					log.info("get take:{}", take);
				}
			}
		}
	}
}
