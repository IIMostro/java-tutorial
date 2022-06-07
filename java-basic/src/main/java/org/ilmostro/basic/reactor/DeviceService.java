package org.ilmostro.basic.reactor;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

/**
 * @author li.bowei
 */
public class DeviceService {

	private static final Map<Integer, Collection<DeviceEntity>> maps = new ConcurrentHashMap<>();

	public Collection<String> getDeviceId(Integer uid) {
		try {
			TimeUnit.SECONDS.sleep(1);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		final List<DeviceEntity> result = Stream.generate(DeviceEntity::new).limit(10).peek(v1 -> v1.setUid(uid))
				.collect(Collectors.toList());
		maps.putIfAbsent(uid, result);
		return result.stream().map(DeviceEntity::getDeviceId).collect(Collectors.toList());
	}


	public DeviceEntity detail(String deviceId) {
		try {
			TimeUnit.SECONDS.sleep(1);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		return maps.values().stream().flatMap(Collection::stream)
				.filter(var1 -> StringUtils.equalsIgnoreCase(deviceId, var1
						.getDeviceId())).findAny().orElse(null);
	}
}
