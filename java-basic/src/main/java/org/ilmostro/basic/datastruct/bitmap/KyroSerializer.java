package org.ilmostro.basic.datastruct.bitmap;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;

import com.esotericsoftware.kryo.kryo5.Kryo;
import com.esotericsoftware.kryo.kryo5.io.Input;
import com.esotericsoftware.kryo.kryo5.io.Output;

/**
 * @author li.bowei
 */
public class KyroSerializer implements Serializable {

	private static final KyroPool pool = KyroPool.getInstance();

	public static byte[] serialize(Object obj) {
		Kryo kryo = pool.obtain();
		try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			 Output output = new Output(byteArrayOutputStream)) {
			// Object->byte:将对象序列化为byte数组
			kryo.writeObject(output, obj);
			return output.toBytes();
		} catch (Exception e) {
			throw new RuntimeException("Serialization failed");
		} finally {
			pool.free(kryo);
		}
	}

	public static <T> T deserialize(byte[] bytes, Class<T> clazz) {
		Kryo kryo = pool.obtain();
		try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
			 Input input = new Input(byteArrayInputStream)) {
			// byte->Object:从byte数组中反序列化出对对象
			Object o = kryo.readObject(input, clazz);
			return clazz.cast(o);
		} catch (Exception e) {
			throw new RuntimeException("Deserialization failed");
		} finally {
			pool.free(kryo);
		}
	}
}
