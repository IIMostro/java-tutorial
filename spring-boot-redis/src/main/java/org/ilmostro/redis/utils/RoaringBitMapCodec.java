package org.ilmostro.redis.utils;

import java.io.ByteArrayInputStream;

import com.esotericsoftware.kryo.kryo5.Kryo;
import com.esotericsoftware.kryo.kryo5.io.Input;
import com.esotericsoftware.kryo.kryo5.io.Output;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufOutputStream;
import org.redisson.client.codec.BaseCodec;
import org.redisson.client.protocol.Decoder;
import org.redisson.client.protocol.Encoder;
import org.roaringbitmap.RoaringBitmap;

/**
 * @author li.bowei
 */
public class RoaringBitMapCodec extends BaseCodec {

	private static final KyroPool pool = KyroPool.getInstance();
	private static final OutputPool outputPool = OutputPool.getInstance();

	private final Decoder<Object> decoder = (buf, state) -> {
		byte[] result = new byte[buf.readableBytes()];
		buf.readBytes(result);
		Kryo kryo = pool.obtain();
		try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(result);
			 Input input = new Input(byteArrayInputStream)) {
			// byte->Object:从byte数组中反序列化出对对象
			return kryo.readObject(input, RoaringBitmap.class);
		} catch (Exception e) {
			throw new RuntimeException("Deserialization failed");
		} finally {
			pool.free(kryo);
		}
	};

	private final Encoder encoder = in -> {
		Kryo kryo = pool.obtain();
		Output output = outputPool.obtain();
		ByteBuf out = ByteBufAllocator.DEFAULT.buffer();
		try {
			ByteBufOutputStream baos = new ByteBufOutputStream(out);
			output.setOutputStream(baos);
			kryo.writeClassAndObject(output, in);
			output.flush();
			return baos.buffer();
		} catch (RuntimeException e) {
			out.release();
			throw e;
		} finally {
			pool.free(kryo);
			outputPool.free(output);
		}
	};

	@Override
	public Decoder<Object> getValueDecoder() {
		return decoder;
	}

	@Override
	public Encoder getValueEncoder() {
		return encoder;
	}
}
