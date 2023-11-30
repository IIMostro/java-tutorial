package org.ilmostro.basic.datastruct.hash;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.hash.Funnel;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.hash.PrimitiveSink;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.ilmostro.basic.User;
import org.junit.Test;

/**
 * @author li.bowei
 */
@Slf4j
public class HashTests {

	private static final Long count = 1_000_000L;

	@Test
	public void basic(){
		final StopWatch watch = new StopWatch();
		watch.start();
		final List<User> users = Stream.generate(User::supplier).limit(count).collect(Collectors.toList());
		watch.stop();
		log.info("create user:[{}]", watch.getTime(TimeUnit.MILLISECONDS));
		watch.reset();
		watch.start();
		final List<Integer> collect = users.stream().map(Objects::hashCode).collect(Collectors.toList());
		watch.stop();
		log.info("basic hash user:[{}]", watch.getTime(TimeUnit.MILLISECONDS));
		watch.reset();
		watch.start();
		final List<Integer> hash = users.stream().map(User::toString).map(v1 -> Hashing.murmur3_128().hashString(v1, StandardCharsets.UTF_8)).map(HashCode::asInt).collect(Collectors.toList());
		watch.stop();
		log.info("basic murmur3 user:[{}]", watch.getTime(TimeUnit.MILLISECONDS));
	}

	@Test
	public void murmur(){
		final User user = User.supplier();
		final HashCode hashCode = Hashing.murmur3_128().hashString(user.toString(), StandardCharsets.UTF_8);
		log.info("hashCode :[{}]", hashCode.toString());
		log.info("asInt :[{}]", hashCode.asInt());
		log.info("asLong :[{}]", hashCode.asLong());
		log.info("asBytes :[{}]", hashCode.asBytes());
	}
}
