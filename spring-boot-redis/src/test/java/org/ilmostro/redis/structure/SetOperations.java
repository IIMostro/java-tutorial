package org.ilmostro.redis.structure;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Set;

/**
 * @author li.bowei
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class SetOperations {

    @Autowired
    private StringRedisTemplate redis;

    @Before
    public void before() {
        redis.opsForSet().add("first", "1", "2", "3");
        redis.opsForSet().add("second", "2", "3", "4");

        redis.opsForZSet().add("first-z", "2", 2);
        redis.opsForZSet().add("first-z", "3", 3);
    }

    @Test
    public void test() {
        Set<String> union = redis.opsForSet().union("first", "second");
        Set<String> difference = redis.opsForSet().difference("first", "second");
        Set<String> intersect = redis.opsForSet().intersect("first", "second");
        log.info("union:{}", union);
        //2021-11-17 13:56:25.839  INFO 15828 --- [           main] o.i.redis.structure.SetOperations        : union:[1, 2, 3, 4]
        log.info("difference:{}", difference);
        //2021-11-17 13:56:26.525  INFO 15828 --- [           main] o.i.redis.structure.SetOperations        : difference:[1]
        log.info("intersect:{}", intersect);
        //2021-11-17 13:56:27.918  INFO 15828 --- [           main] o.i.redis.structure.SetOperations        : intersect:[2, 3]
    }

    @Test
    public void testIntersect() {
        redis.opsForZSet().intersectAndStore("first-z", "first", "first-o");
        log.info("intersect zset:{}", redis.opsForZSet().range("first-o", 0, -1));
    }

    @After
    public void after() {
        redis.delete(Arrays.asList("first", "second", "first-z"));
    }
}
