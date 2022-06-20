package org.ilmostro.redis.redisson.queue;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;

import org.springframework.stereotype.Service;

/**
 * @author li.bowei
 */
@Service
@Slf4j
public class BlockingQueueService {

	private final RBlockingQueue<String> queue;

	public BlockingQueueService(RedissonClient redisson) {
		this.queue = redisson.getBlockingQueue("r-blocking-queue-test", new JsonJacksonCodec());
		Executors.newSingleThreadExecutor().submit(new QueueTakeTask());
	}

	public void offer(String message, long time, TimeUnit unit) {
		try {
			this.queue.offer(message, time, unit);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private class QueueTakeTask implements Runnable {
		@Override
		public void run() {
			while (true) {
				try {
					final String take = queue.take();
					log.info("take event:[{}]", take);
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
