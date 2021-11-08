package org.ilmostro.redis.structure;

import lombok.extern.slf4j.Slf4j;
import org.ilmostro.redis.domain.User;
import org.ilmostro.redis.utils.ObjectMapperUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author li.bowei
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class ZSetOperations {

    @Autowired
    private StringRedisTemplate redis;

    @Test
    public void test() {
        BoundZSetOperations<String, String> operations = redis.boundZSetOps("zset_test");
        operations.removeRange(0, -1);
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            User user = new User();
            users.add(user);
            user.setId(i);
            user.setAge(i);
        }
        operations.add(users.stream().map(var1 -> new DefaultTypedTuple<>(ObjectMapperUtils.toJSONString(var1), (double) var1.getId())).collect(Collectors.toSet()));
        operations.removeRange(20, 20);
        operations.removeRangeByScore(19, 19);
        Set<String> range = operations.range(0, -1);
        log.info("range:{}", range);

        Set<User> collect = range.stream().map(var1 -> ObjectMapperUtils.toJavaObject(var1, User.class)).collect(Collectors.toSet());
        for (User user : collect) {
            log.info("user:{}", user);
        }
    }
}
