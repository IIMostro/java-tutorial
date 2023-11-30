package org.ilmostro.basic.datastruct.map;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;
import org.ilmostro.basic.User;
import org.junit.Test;

/**
 * @author li.bowei
 */
@Slf4j
public class ConcurrentHashMapReducer {

	@Test
	public void test() {
		ConcurrentHashMap<Integer, User> collect = Stream.generate(User::supplier).limit(100000)
				.collect(Collectors.toMap(User::getId, Function.identity(), (v1, v2) -> v1, ConcurrentHashMap::new));
		Map<String, BigDecimal> reduce = collect.reduce(2, (v1, v2) -> v2.getScores().stream()
				.collect(Collectors.toMap(User.Score::getSubject, User.Score::getScore, BigDecimal::add)), (v1, v2) -> {
			for (String key : v1.keySet()) {
				BigDecimal bigDecimal = v2.get(key);
				v1.computeIfPresent(key, (v3, v4) -> v4.add(Objects.isNull(bigDecimal) ? BigDecimal.ZERO : bigDecimal));
			}
			return v1;
		});
		log.info("reduce:[{}]", reduce);
	}

	@Test
	public void wordCount() {
	}
}
