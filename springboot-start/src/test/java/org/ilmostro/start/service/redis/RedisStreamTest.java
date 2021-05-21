package org.ilmostro.start.service.redis;

import org.ilmostro.start.redis.RedisConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.connection.stream.StringRecord;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author li.bowei
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisStreamTest {

    @Autowired
    private RedisTemplate<String, Object> template;

    @Test
    public void producer() throws InterruptedException {
        Map<String, String> parameter = new HashMap<>();
        for(int i = 0; i < 10000; i++){
            parameter.clear();
            parameter.putIfAbsent("order", String.valueOf(i));
            StringRecord record = StreamRecords.string(parameter).withStreamKey(RedisConfiguration.REDIS_STREAM_NAME);
            template.opsForStream().add(record);
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
