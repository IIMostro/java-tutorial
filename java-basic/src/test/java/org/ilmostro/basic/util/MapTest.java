package org.ilmostro.basic.util;

import io.vavr.Function2;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author li.bowei
 */
@Slf4j
public class MapTest {

    @Test
    public void absent(){
        Map<Integer, List<String>> map = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            int key = i % 9;
            List<String> strings = map.computeIfAbsent(key, integer -> new ArrayList<>());
            strings.add(String.valueOf(i));
        }
        map.forEach((key, value) -> log.info("key:{}, value:{}", key, value));
    }

    @Test
    public void present(){
        Map<Integer, List<String>> map = new HashMap<>();
        map.put(1, new ArrayList<>());
        for (int i = 0; i < 100; i++) {
            int key = i % 9;
            final int finalI = i;
            map.computeIfPresent(key, (integer, strings1) -> {
                strings1.add(String.valueOf(finalI));
                return strings1;
            });
        }
        map.forEach((key, value) -> log.info("key:{}, value:{}", key, value));
    }

    @Test
    public void compute(){
        Map<Integer, List<String>> map = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            int key = i % 9;
            List<String> compute = map.compute(key, (Function2<Integer, List<String>, List<String>>) (integer, strings) -> {
                if (strings == null) return new ArrayList<>();
                return strings;
            });
            compute.add(String.valueOf(i));
        }
        map.forEach((key, value) -> log.info("key:{}, value:{}", key, value));
    }
}
