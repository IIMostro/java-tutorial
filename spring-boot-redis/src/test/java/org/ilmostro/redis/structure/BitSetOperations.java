package org.ilmostro.redis.structure;

import io.jsonwebtoken.lang.Collections;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author li.bowei
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class BitSetOperations {

    @Autowired
    private StringRedisTemplate redis;

    @Test
    public void set(){
        redis.opsForValue().setBit("bit:1:202202", 0, true);
        redis.opsForValue().setBit("bit:1:202202", 1, true);
        redis.opsForValue().setBit("bit:1:202202", 2, true);
        redis.opsForValue().setBit("bit:1:202202", 3, true);
        redis.opsForValue().setBit("bit:1:202202", 4, false);
        redis.opsForValue().setBit("bit:1:202202", 5, true);
        redis.opsForValue().setBit("bit:1:202202", 6, false);
    }

    @Test
    public void get(){
        for (int i = 0; i < 6; i++) {
            Boolean bit = redis.opsForValue().getBit("bit:1:202202", i);
            log.info("curr bit is:{}", bit);
        }
    }

    @Test
    public void commands(){
        BitFieldSubCommands commands = BitFieldSubCommands.create()
                .get(BitFieldSubCommands.BitFieldType.unsigned(6)).valueAt(0);
        List<Long> longs = redis.opsForValue().bitField("bit:1:202202", commands);
        if(Collections.isEmpty(longs)) return;
        for (Long value : longs) {
            log.info("commands: {}", Long.toBinaryString(value));
        }
    }

}
