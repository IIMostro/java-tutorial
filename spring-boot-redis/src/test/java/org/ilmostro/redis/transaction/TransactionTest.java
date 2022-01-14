package org.ilmostro.redis.transaction;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.lang.NonNull;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author li.bowei
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class TransactionTest {

    @Autowired
    private StringRedisTemplate template;

    @Test
    public void watch() throws ExecutionException, InterruptedException {
        Callable<Object> callable = () -> template.execute(new SessionCallback<Object>() {
            @Override
            public Object execute(@NonNull RedisOperations operations) throws DataAccessException {
                operations.watch("hello");
                operations.multi();
                operations.opsForValue().increment("hello");
                return operations.exec();
            }
        });
        ExecutorService executor = Executors.newFixedThreadPool(4);
        List<Future<?>> futures = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Future<Object> submit = executor.submit(callable);
            futures.add(submit);
        }
        for (Future<?> future : futures) {
            log.info("future:{}", future.get());
        }
    }
}
