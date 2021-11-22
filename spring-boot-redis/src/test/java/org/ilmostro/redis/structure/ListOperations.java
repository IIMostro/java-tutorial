package org.ilmostro.redis.structure;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ListOperations {

    @Autowired
    private StringRedisTemplate redis;

    @Test
    public void test(){
        redis.opsForList().set("limit", System.currentTimeMillis(), "1");
    }
}
