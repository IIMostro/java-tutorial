package org.ilmostro.redis.support;

import java.util.concurrent.TimeUnit;

import lombok.Data;

/**
 * @author li.bowei
 */
@Data
public class ExpireCommandMessage {

	public static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.SECONDS;
	private String key;
	private String value;
	private Long expire;
	private String command;

	ExpireCommandMessage(String key, String value, Long expire, String command) {
		this.key = key;
		this.value = value;
		this.expire = expire;
		this.command = command;
	}
}
