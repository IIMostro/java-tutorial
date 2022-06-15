package org.ilmostro.redis.utils;

import java.io.ByteArrayOutputStream;

import com.esotericsoftware.kryo.kryo5.io.Output;
import com.esotericsoftware.kryo.kryo5.util.Pool;

/**
 * @author li.bowei
 */
public class OutputPool extends Pool<Output> {

	public static OutputPool getInstance() {
		return LazyHolder.INSTANCE;
	}

	public OutputPool(boolean threadSafe, boolean softReferences) {
		super(threadSafe, softReferences);
	}

	private static class LazyHolder{
		private static final OutputPool INSTANCE = new OutputPool(true, false);
	}

	@Override
	protected Output create() {

		try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			 Output output = new Output(byteArrayOutputStream)) {
			return output;
		}catch (Exception exception){
			//ignore
		}
		return null;
	}
}
