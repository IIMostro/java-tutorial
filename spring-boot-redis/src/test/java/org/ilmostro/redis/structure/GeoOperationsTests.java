package org.ilmostro.redis.structure;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.BoundGeoOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author li.bowei
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class GeoOperationsTests {

	@Autowired
	private StringRedisTemplate template;

	BoundGeoOperations<String, String> operations;

	@Before
	public void before() {
		operations = template.boundGeoOps("hangzhou");
		final List<RedisGeoCommands.GeoLocation<String>> geos = Arrays.asList(new RedisGeoCommands.GeoLocation<>("天堂软件园", new Point(120.123703, 30.297402)),
				new RedisGeoCommands.GeoLocation<>("断桥", new Point(120.142424, 30.257169)),
				new RedisGeoCommands.GeoLocation<>("灵隐寺", new Point(120.107138, 30.246655)),
				new RedisGeoCommands.GeoLocation<>("鼓楼", new Point(120.177637, 30.243723)),
				new RedisGeoCommands.GeoLocation<>("长河", new Point(120.200778, 30.202718)),
				new RedisGeoCommands.GeoLocation<>("杭州南站", new Point(120.301675, 30.178307)));
		operations.add(geos);
	}

	@Test
	public void distance() {
		final Distance distance = operations.distance("天堂软件园", "断桥");
		log.info("天堂软件园 距离 断桥: [{}:{}]", distance.getValue(), distance.getUnit());
		final Distance distance1 = operations.distance("断桥", "杭州南站");
		log.info("断桥距离杭州南站:[{}:{}]", distance1.getValue(), distance1.getUnit());

		final GeoResults<RedisGeoCommands.GeoLocation<String>> radius =
				operations.radius("断桥", new Distance(20, Metrics.KILOMETERS), RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().includeDistance().includeCoordinates().sortAscending());

		if (Objects.isNull(radius)) {
			log.info("断桥10km范围内没有找到地方");
			return;
		}

		final String[] positions = Arrays.asList("杭州东站", "杭州南站", "杭州西站").toArray(new String[0]);
		final List<Point> result = operations.position(positions);
		for (int i = 0; i < result.size(); i++) {
			log.info("index:[{}], value:[{}]", i, result.get(i));
		}

		for (GeoResult<RedisGeoCommands.GeoLocation<String>> next : radius) {
//			final Distance distance2 = operations.distance("断桥", next.getContent().getName(), Metrics.KILOMETERS);
			log.info("断桥 10KM范围内有:[{}] 位置是:{}, 距离: {}:{}", next.getContent().getName(), next.getContent().getPoint(), next.getDistance().getValue(), next.getDistance().getUnit());
		}
	}
}
