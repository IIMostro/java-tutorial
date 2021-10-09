package org.ilmostro.start.service.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RHyperLogLog;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author li.bowei
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class HyperLogLogTest {

    @Autowired
    private RedissonClient redisson;

    @Test
    public void test(){
        RHyperLogLog<Object> id = redisson.getHyperLogLog("id");
    }
}
