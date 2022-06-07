package org.ilmostro.basic.reactor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

import cn.hutool.core.lang.func.Func;
import io.vavr.Function2;
import lombok.extern.slf4j.Slf4j;
import org.ilmostro.basic.executor.ThreadPoolExecutorFactory;
import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

/**
 * @author li.bowei
 */
@Slf4j
public class ReactorServiceTests {

	private static final DeviceService service = new DeviceService();
	private static final ThreadPoolExecutor executor = ThreadPoolExecutorFactory.get(10, false, "customize");


	@Test
	public void simple() {
		final Scheduler scheduler = Schedulers.fromExecutor(executor);
		final Map<Integer, List<DeviceEntity>> maps = Flux.fromIterable(Arrays.asList(1, 2, 3))
				.parallel()
				.runOn(scheduler)
				.map(service::getDeviceId)
				.sequential()
				.map(Collection::stream)
				.map(v1 -> v1.collect(Collectors.toList()))
				.flatMapIterable(ArrayList::new)
				.parallel()
				.runOn(scheduler)
				.map(service::detail)
				.log()
				.sequential()
				.collect(Collectors.groupingBy(DeviceEntity::getUid))
				.block();
		log.info("map keys:[{}], values:[{}]", maps.keySet().size(), maps.values().stream().flatMap(Collection::stream)
				.count());
	}

	@Test
	public void block(){
		final List<Integer> users = Arrays.asList(1, 2, 3);
		Map<Integer, List<DeviceEntity>> maps = new HashMap<>();
		for (Integer user : users) {
			final Collection<String> devices = service.getDeviceId(user);
			for (String device : devices) {
				maps.computeIfAbsent(user, ArrayList::new);
				maps.get(user).add(service.detail(device));
			}
		}
		log.info("map keys:[{}], values:[{}]", maps.keySet().size(), maps.values().stream().flatMap(Collection::stream)
				.count());
	}

}
