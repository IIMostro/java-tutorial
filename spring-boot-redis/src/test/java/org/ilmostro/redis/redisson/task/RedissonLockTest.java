package org.ilmostro.redis.redisson.task;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * @author li.bowei
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class RedissonLockTest {

    @Autowired
    private RedissonClient redisson;

    @Test
    public void lock() throws InterruptedException {
        RLock lock = redisson.getLock("lock");
        try {
            lock.lock(50, TimeUnit.SECONDS);
            log.info("hello");
        } finally {
            lock.unlock();
        }
    }
}
