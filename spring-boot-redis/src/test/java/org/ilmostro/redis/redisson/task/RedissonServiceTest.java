package org.ilmostro.redis.redisson.task;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * @author li.bowei
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedissonServiceTest {

    @Autowired
    private RedissonService service;

    @Test
    public void test() throws InterruptedException {
        service.schedule();
        for (long i = 0L; i < 100; i++) {
            service.setCollection(i);
            TimeUnit.SECONDS.sleep(1);
        }
    }

}