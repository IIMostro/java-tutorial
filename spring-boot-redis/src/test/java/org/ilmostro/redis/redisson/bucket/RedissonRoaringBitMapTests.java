package org.ilmostro.redis.redisson.bucket;

import java.util.Objects;

import io.reactivex.rxjava3.core.Completable;
import lombok.extern.slf4j.Slf4j;
import org.ilmostro.redis.domain.User;
import org.ilmostro.redis.utils.RoaringBitMapCodec;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.codec.Kryo5Codec;
import org.roaringbitmap.RoaringBitmap;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author li.bowei
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class RedissonRoaringBitMapTests {

	@Autowired
	private RedissonClient redisson;

	@Test
	public void bucket() {
		final RBucket<RoaringBitmap> bucket = redisson.getBucket("roaring-bitmap",
				new RoaringBitMapCodec());
		RoaringBitmap bitmap = bucket.get();
		if (Objects.isNull(bitmap)) bitmap = new RoaringBitmap();
		for (int i = 0; i < 1000; i++) {
			bitmap.add(i);
		}
		bucket.set(bitmap);
	}

	@Test
	public void getBucket() {
		final RBucket<RoaringBitmap> bucket = redisson.getBucket("roaring-bitmap",
				new RoaringBitMapCodec());
		final RoaringBitmap bitmap = bucket.get();
		for (int i = 0; i < 1000; i++) {
			log.info("contains:{}", bitmap.contains(i));
		}
	}

	@Test
	public void update(){
		final RBucket<RoaringBitmap> bucket = redisson.getBucket("roaring-bitmap",
				new RoaringBitMapCodec());
		final RoaringBitmap bitmap = bucket.get();
		for (int i = 0; i < 1000; i++) {
			if (i % 2 == 0) bitmap.remove(i);
		}
		bucket.set(bitmap);
	}

	@Test
	public void simple(){
		final RBucket<User> bucket = redisson.getBucket("simple-user", new Kryo5Codec());
		final User supplier = User.supplier();
		bucket.setIfExists(supplier);
		final User user = bucket.get();
		log.info("user:{}", user);
	}
}
