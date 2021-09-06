package org.ilmostro.start.service.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author li.bowei
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTopicTest {

    @Autowired
    private StringRedisTemplate template;

    @Test
    public void publisher(){

        for (int i = 0; i < 20; i++) {
            template.convertAndSend("topic:first", String.valueOf(i));
        }
    }
}
