package org.ilmostro.test.template.stream;

import lombok.extern.slf4j.Slf4j;
import org.ilmostro.test.domian.UserEntity;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author li.bowei on 2019-10-12.
 * @description
 */
@Slf4j
public class StreamMethodTest {

    private StreamMethod method;

    @Before
    public void before(){
        this.method = new StreamMethod();
    }

    @Test
    public void map() {
        method.map().stream().map(String::valueOf).forEach(log::info);
    }

    @Test
    public void flatMap() {
        method.flatMap().stream().map(Objects::toString).forEach(log::info);
    }

    @Test
    public void filter() {
        method.filter().stream().map(Objects::toString).forEach(log::info);
    }

    @Test
    public void reduce() {
        log.info("reduce {} ", method.reduce());
    }

    @Test
    public void sort() {
        method.sort().stream().map(Objects::toString).forEach(log::info);
    }

    @Test
    public void collection() {
        Map<Integer, List<UserEntity>> collection = method.collection();
        for(Map.Entry<Integer, List<UserEntity>> entry: collection.entrySet()){
            String users = entry.getValue().stream().map(UserEntity::getName).reduce((o1, o2) -> o1 + o2).orElse("");
            log.info("age={} have user {}", entry.getKey(), users);
        }
    }

    @Test
    public void parallel(){
        method.parallel();
    }
}