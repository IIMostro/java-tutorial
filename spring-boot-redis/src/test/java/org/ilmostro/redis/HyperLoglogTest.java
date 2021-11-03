package org.ilmostro.redis;

import cn.hutool.core.lang.MurmurHash;
import lombok.extern.slf4j.Slf4j;
import org.ilmostro.redis.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RHyperLogLog;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author li.bowei
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class HyperLoglogTest {

    @Autowired
    private RedissonClient redissonClient;

    @Test
    public void test(){
        RHyperLogLog<Integer> hello = redissonClient.getHyperLogLog("hello");
        RHyperLogLog<Integer> world = redissonClient.getHyperLogLog("world");
        List<User> users = Stream.generate(User::supplier).limit(1000).collect(Collectors.toList());
        Set<Integer> collect = users.stream().map(User::getName).map(MurmurHash::hash32).collect(Collectors.toSet());
        hello.addAll(collect);
        world.add(MurmurHash.hash32("a"));
        long count = hello.countWith("world");
        log.info("count:{}", count);
    }
}
