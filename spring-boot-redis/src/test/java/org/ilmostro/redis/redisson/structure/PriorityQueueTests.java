package org.ilmostro.redis.redisson.structure;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RPriorityQueue;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author li.bowei
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class PriorityQueueTests {

	@Autowired
	private RedissonClient redisson;

	@Test
	public void test() {
		final RPriorityQueue<Ranking> queue = redisson.getPriorityQueue("priority_queue_tuple_test", JsonJacksonCodec.INSTANCE);
		queue.trySetComparator(new Ranking());
		List<Ranking> list = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			list.add(new Ranking(i, i));
		}
		queue.addAll(list);
		final Ranking rank = queue.poll();
		log.info("{}", rank);
	}
}
