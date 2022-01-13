package org.ilmostro.redis.transaction;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author li.bowei
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionTest {

    @Autowired
    private StringRedisTemplate template;

    @Test
    public void watch(){
        ExecutorService executor = Executors.newFixedThreadPool(4);
        ValueOperations<String, String> operations = template.opsForValue();
        template.execute(new SessionCallback<String>() {
            @Override
            public <K, V> String execute(RedisOperations<K, V> redisOperations) throws DataAccessException {
                return null;
            }
        });
    }
}
