package org.ilmostro.basic.map;

import lombok.extern.slf4j.Slf4j;
import org.ilmostro.basic.User;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author li.bowei
 */
@Slf4j
public class ConcurrentHashMapReducer {

    @Test
    public void test() {
        ConcurrentHashMap<Integer, User> collect = Stream.generate(User::supplier).limit(100000).collect(Collectors.toMap(User::getId, Function.identity(), (v1, v2) -> v1, ConcurrentHashMap::new));
        Map<String, BigDecimal> reduce = collect.reduce(2, (v1, v2) -> v2.getScores().stream().collect(Collectors.toMap(User.Score::getSubject, User.Score::getScore, BigDecimal::add)), (v1, v2) -> {
            for (String key : v1.keySet()) {
                BigDecimal bigDecimal = v2.get(key);
                v1.computeIfPresent(key, (v3, v4) -> v4.add(Objects.isNull(bigDecimal) ? BigDecimal.ZERO : bigDecimal));
            }
            return v1;
        });
        log.info("reduce:[{}]", reduce);
    }

    @Test
    public void wordCount(){

        ConcurrentHashMap<String, String> concurrent = new ConcurrentHashMap<>();
        concurrent.put("1", "dfadfdfadgasdfasdfasdfasfd");
        concurrent.put("2", "dcvkjlbsfkldjgsogklfjg");
        concurrent.put("3", "dfadfdfadgasdfasdfasdfasfd");
        concurrent.put("4", "dfadfdfadgasdfasdfasdfasfd");

        Map<String, Long> reduce = concurrent.reduce(2, (v1, v2) -> Arrays.stream(v2.split("[^a-zA-Z]"))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting())), (v1, v2) -> {
            for (String key : v1.keySet()) {
                v1.compute(key, (v3, v4) -> v2.get(v3) + v4);
            }
            return v1;
        });

        log.info("reduce:[{}]", reduce);
    }
}
