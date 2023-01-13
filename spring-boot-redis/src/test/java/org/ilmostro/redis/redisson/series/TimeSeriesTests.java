package org.ilmostro.redis.redisson.series;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.RTimeSeries;
import org.redisson.api.RedissonClient;
import org.redisson.codec.Kryo5Codec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author li.bowei
 */
@SpringBootTest
@Slf4j
public class TimeSeriesTests {

	@Autowired
	private RedissonClient redisson;

	@Test
	void test(){
		final RTimeSeries<Object, Object> series = redisson.getTimeSeries("test:time:series", new Kryo5Codec());
		final long current = System.currentTimeMillis();
		series.add(current, "hello", "application=test");
		log.info("{}", series.get(current));
		series.entryRange(current, current + 1000).forEach(v -> log.info("value:{}", v));
	}
}
