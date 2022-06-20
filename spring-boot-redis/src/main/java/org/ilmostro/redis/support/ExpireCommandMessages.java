package org.ilmostro.redis.support;

import java.util.concurrent.TimeUnit;

/**
 * @author li.bowei
 */
public class ExpireCommandMessages {

	public static MapBuilder map() {
		return new MapBuilder();
	}

	public static SetBuilder set(){
		return new SetBuilder();
	}

	public static final class MapBuilder extends KeyValueExpireBuilder {
		public static final String MAP_EXPIRE_COMMAND_MESSAGE = "map";

		public ExpireCommandMessage build() {
			return new ExpireCommandMessage(key, value, unit.toSeconds(expire), MAP_EXPIRE_COMMAND_MESSAGE);
		}
	}

	public static final class SetBuilder extends KeyValueExpireBuilder {
		public static final String MAP_EXPIRE_COMMAND_MESSAGE = "set";

		public ExpireCommandMessage build() {
			return new ExpireCommandMessage(key, value, unit.toSeconds(expire), MAP_EXPIRE_COMMAND_MESSAGE);
		}
	}


	public static class KeyValueExpireBuilder {

		protected String key;
		protected String value;
		protected Long expire;
		protected TimeUnit unit;

		public KeyValueExpireBuilder key(String key) {
			this.key = key;
			return this;
		}

		public KeyValueExpireBuilder value(String value) {
			this.value = value;
			return this;
		}

		public KeyValueExpireBuilder expire(Long expire) {
			this.expire = expire;
			return this;
		}

		public KeyValueExpireBuilder unit(TimeUnit unit) {
			this.unit = unit;
			return this;
		}

		public ExpireCommandMessage build() {
			throw new UnsupportedOperationException();
		}
	}
}
