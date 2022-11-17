package org.ilmostro.redis.redisson.limiter;

import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RHyperLogLog;
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
    public void test() throws Exception{
        RBloomFilter<Object> filter = redissonClient.getBloomFilter("bloom");
        filter.tryInit(1000, 0.03);
        for (int i = 0; i < 10; i++) {
            filter.add(i % 3);
            TimeUnit.SECONDS.sleep(10);
            log.info("filter contains:[{}]", filter.contains(i));
        }
    }

}
