package org.ilmostro.redis.stack;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.github.dengliming.redismodule.redistimeseries.Label;
import io.github.dengliming.redismodule.redistimeseries.RangeOptions;
import io.github.dengliming.redismodule.redistimeseries.RedisTimeSeries;
import io.github.dengliming.redismodule.redistimeseries.Sample;
import io.github.dengliming.redismodule.redistimeseries.TimeSeriesOptions;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.redisson.Redisson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author li.bowei
 */
@SpringBootTest
@Slf4j
public class RedisTimeSeriesTests {

	@Autowired
	private Redisson redisson;

	private RedisTimeSeries series;

	@BeforeEach
	void before(){
		series = new RedisTimeSeries(redisson.getCommandExecutor());
	}

	@Test
	void test(){
		final long timestamp = System.currentTimeMillis();
		series.add(new Sample("temperature:2:32", Sample.Value.of(timestamp, 26)), new TimeSeriesOptions()
				.retentionTime(6000L)
				.unCompressed()
				.labels(new Label("sensor_id", "2"), new Label("area_id", "32")));
	}

	@Test
	void add(){
		series.add(new Sample("temperature:2:32", Sample.Value.of(System.currentTimeMillis(), 35)), new TimeSeriesOptions()
				.retentionTime(6000L)
				.unCompressed()
				.labels(new Label("custom_id", "2")));
	}


	@Test
	void add_more() throws Exception{
		for (int i = 'a'; i < 'z'; i++) {
			final long now = System.currentTimeMillis();
			series.add(new Sample("temperature:2:32", Sample.Value.of(now, 35)), new TimeSeriesOptions()
					.retentionTime(1000 * 60 * 60)
					.unCompressed()
					.labels(new Label(String.valueOf((char) i), String.valueOf(now))));
			TimeUnit.SECONDS.sleep(1);
		}
	}

	@Test
	void range(){
		final List<Sample.Value> range = series.range("temperature:2:32",0, System.currentTimeMillis());
		for (Sample.Value value : range) {
			log.info("value:[{}]", value.getValue());
		}
	}

	@Test
	void range_filter(){
		final List<Sample.Value> range = series.range("temperature:2:32",0, System.currentTimeMillis(),
				new RangeOptions().withLabels());
		for (Sample.Value value : range) {
			log.info("value:[{}]", value.getValue());
		}
	}

	@Test
	void info(){
		final Map<String, Object> info = series.info("temperature:2:32");
		for (Map.Entry<String, Object> entry : info.entrySet()) {
			log.info("key:[{}], value:[{}]", entry.getKey(), entry.getValue());
		}
	}

	@Test
	void get(){
		final Sample.Value value = series.get("temperature:2:32");

	}
}
