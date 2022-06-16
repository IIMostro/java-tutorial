package org.ilmostro.redis.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.esotericsoftware.kryo.kryo5.Kryo;
import com.esotericsoftware.kryo.kryo5.io.Input;
import com.esotericsoftware.kryo.kryo5.io.Output;
import org.roaringbitmap.RoaringBitmap;

/**
 * @author li.bowei
 */
public class RoaringBitMapUtils {

	public static RoaringBitmap read(InputStream is) {
		final KyroPool pool = KyroPool.getInstance();
		Kryo kryo = pool.obtain();
		try (Input input = new Input(is)) {
			// byte->Object:从byte数组中反序列化出对对象
			return kryo.readObject(input, RoaringBitmap.class, new RoaringSerializer());
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		finally {
			pool.free(kryo);
		}
	}

	public static RoaringBitmap read(byte[] bytes) {
		final KyroPool pool = KyroPool.getInstance();
		Kryo kryo = pool.obtain();
		try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
			 Input input = new Input(byteArrayInputStream)) {
			// byte->Object:从byte数组中反序列化出对对象
			return kryo.readObject(input, RoaringBitmap.class);
		}
		catch (Exception e) {
			throw new RuntimeException("Deserialization failed");
		}
		finally {
			pool.free(kryo);
		}
	}

	public static byte[] write(RoaringBitmap bitmap) {
		final KyroPool pool = KyroPool.getInstance();
		final OutputPool outputPool = OutputPool.getInstance();
		Kryo kryo = pool.obtain();
		Output output = outputPool.obtain();
		try (ByteArrayOutputStream os = new ByteArrayOutputStream()){
			output.setOutputStream(os);
			kryo.writeClassAndObject(output, bitmap);
			output.flush();
			return os.toByteArray();
		}
		catch (RuntimeException | IOException e) {
			// ignore
			throw new RuntimeException(e);
		}
		finally {
			pool.free(kryo);
			outputPool.free(output);
		}
	}
}
