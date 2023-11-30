package org.ilmostro.basic.datastruct.map;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

/**
 * @author li.bowei
 */
@Slf4j
public class MapFunctionTest {

	Map<String, Integer> map;

	@Before
	public void before(){
		map = new HashMap<>();
		for (int i = 0; i < 10; i++) {
			map.putIfAbsent(String.valueOf(i), i);
		}
	}

	@Test
	public void compute(){
		for (String key : map.keySet()) {
			map.compute(key, (v1, v2) -> v2 * 10);
		}
		map.forEach((key, value) -> log.info("key:{}, value:{}", key, value));
	}

	@Test
	public void computeIfPresent(){
		// 如果当前map存在这个值的话则重新计算
		map.computeIfPresent(String.valueOf(0), (v1, v2) -> 100);
		log.info("compute if present zero, value:{}", map.get(String.valueOf(0)));
	}

	@Test
	public void computeIfAbsent(){
		Function<String, Integer> getRandom = v1 -> new Random().nextInt();
		map.computeIfAbsent(String.valueOf(100), getRandom);
		log.info("compute if absent zero, value:{}", map.get(String.valueOf(100)));
	}
}
