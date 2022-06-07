package org.ilmostro.basic.reactor;

import java.util.UUID;

import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author li.bowei
 */
@Data
@ToString
public class DeviceEntity {

	private Integer uid;
	private String deviceId;
	private String deviceName;

	public DeviceEntity() {
		this.deviceId = UUID.randomUUID().toString();
		this.deviceName = RandomStringUtils.random(10, true, false);
	}
}
