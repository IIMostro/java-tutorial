package org.ilmostro.redis.redisson.structure;

import lombok.extern.slf4j.Slf4j;
import org.ilmostro.redis.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RedissonClient;
import org.redisson.api.mapreduce.RCollectionMapper;
import org.redisson.api.mapreduce.RCollector;
import org.redisson.api.mapreduce.RReducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author li.bowei
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class MapReduceTest {

    @Autowired
    private RedissonClient redisson;

    @Before
    public void before() {

        List<User> users = Stream.generate(User::supplier).limit(100).collect(Collectors.toList());
        redisson.<User>getSet("user-filter").addAll(users.subList(0, 10));
        redisson.<User>getSet("user").addAll(users);
    }

    public static class CustomMapper implements RCollectionMapper<User, String, Integer> {

        private final Set<User> users;

        public CustomMapper(Set<User> users) {
            this.users = users;
        }

        @Override
        public void map(User o, RCollector<String, Integer> rCollector) {
            rCollector.emit(o.getName(), o.getScores().stream().map(User.Score::getScore).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).intValue());
        }
    }

    public static class CustomReducer implements RReducer<String, Integer> {

        @Override
        public Integer reduce(String reducedKey, Iterator<Integer> iter) {
            Integer sum = 0;
            while (iter.hasNext()) {
                sum += iter.next();
            }
            return sum;
        }
    }

    @Test
    public void test() {
        Set<User> users = redisson.<User>getSet("user-filter").readIntersection("user");
        Map<String, Integer> execute = redisson.<User>getSet("user").<String, Integer>mapReduce()
                .mapper(new CustomMapper(users))
                .reducer(new CustomReducer()).execute();
        log.info("map reduce:{}", execute);
    }
}
