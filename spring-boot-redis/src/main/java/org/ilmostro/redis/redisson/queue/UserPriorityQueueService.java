package org.ilmostro.redis.redisson.queue;

import lombok.extern.slf4j.Slf4j;
import org.ilmostro.redis.domain.User;
import org.ilmostro.redis.redisson.UserComparator;
import org.ilmostro.redis.utils.ObjectMapperUtils;
import org.redisson.api.RPriorityQueue;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.codec.Kryo5Codec;

import org.springframework.stereotype.Service;

/**
 * @author li.bowei
 */
@Service
@Slf4j
public class UserPriorityQueueService {

	private final RPriorityQueue<User> queue;
	private static final String USER_PRIORITY_QUEUE_KEY = "user-priority-queue";
	private static final int size = 10;

	UserPriorityQueueService(RedissonClient redisson){
		this.queue = redisson.getPriorityQueue(USER_PRIORITY_QUEUE_KEY, new JsonJacksonCodec());
		this.queue.trySetComparator(new UserComparator());
	}

	public void add(User user){
		queue.offer(user);
		if (queue.size() > size) {
			final User poll = queue.poll();
			log.info("user:[{}] will be castoff", ObjectMapperUtils.toJSONString(poll));
		}
	}
}
