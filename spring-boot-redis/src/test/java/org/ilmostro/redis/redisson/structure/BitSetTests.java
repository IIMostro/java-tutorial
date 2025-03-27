package org.ilmostro.redis.redisson.structure;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class BitSetTests {

    @Autowired
    private RedissonClient redisson;

    @Test
    void test_bit_set_should_work() throws Exception{
        final var bitset = redisson.getBitSet("test:bitset");
        bitset.clear();
        bitset.set(0, 3, true);
        bitset.asBitSet().stream().forEach(v1 -> log.info("get bit value:{}", v1));
        bitset.set(0, false);
        bitset.asBitSet().stream().forEach(v1 -> log.info("get bit value:{}", v1));
    }
}
