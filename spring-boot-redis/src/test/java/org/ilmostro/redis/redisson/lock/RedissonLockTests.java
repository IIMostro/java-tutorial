package org.ilmostro.redis.redisson.lock;

import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.RLockReactive;
import org.redisson.api.RedissonReactiveClient;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author li.bowei
 */
@SpringBootTest
@Slf4j
public class RedissonLockTests {

	@Autowired
	private RedissonReactiveClient redisson;

	@Test
	void reactor_lock() throws InterruptedException {
		String lock_name = "test_reactive_lock";
		final RLockReactive lock = redisson.getLock(lock_name);
		lock.tryLock(10, TimeUnit.SECONDS)
				.then(Mono.fromRunnable(this::test_time_business))
				.then(lock.forceUnlock())
				.subscribe(v1 -> log.info("lock result:{}", v1),
						err -> log.error("lock error", err),
						() -> lock.isLocked().subscribe(v1 -> log.info("lock is locked:{}", v1)));
		TimeUnit.SECONDS.sleep(3);
	}

	void test_time_business() {
		try {
			log.info("start business");
			TimeUnit.SECONDS.sleep(1);
			log.info("end business");
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
