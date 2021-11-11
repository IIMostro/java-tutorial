package org.ilmostro.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author li.bowei
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class BloomFilterTest {

    @Autowired
    private RedissonClient redissonClient;

    @Test
    public void test(){
        RBloomFilter<Object> filter = redissonClient.getBloomFilter("bloom");
        RSet<Object> a = redissonClient.getSet("a");
    }

}
