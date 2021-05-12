package org.ilmostro.start.service.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Objects;
import java.util.Set;

/**
 * @author li.bowei
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class RedisTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void publish(){
        for(int i = 0; i< 10; i++){
            stringRedisTemplate.convertAndSend("test-topic", "this is count:" + i + ",message");
        }
    }

    @Test
    public void zset(){
        for(int i = 0; i < 10; i++){
            stringRedisTemplate.opsForZSet().add("test-zset", String.valueOf(i), i);
        }
    }

    @Test
    @Ignore
    public void zsetTest1(){
    }

    @Test
    public void zsetGet(){
        boolean flag = true;
        do {
            Set<String> range = stringRedisTemplate.opsForZSet().range("test-zset", 0, 0);
            if(Objects.isNull(range) || range.isEmpty()){
                flag = false;
                continue;
            }
            stringRedisTemplate.opsForZSet().removeRange("test-zset", 0, 0);
            log.info("range:{}", range);
        }while (flag);
    }
}
