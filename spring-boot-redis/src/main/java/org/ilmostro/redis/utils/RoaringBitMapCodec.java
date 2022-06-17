package org.ilmostro.redis.utils;

import java.nio.ByteBuffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import org.redisson.client.codec.BaseCodec;
import org.redisson.client.protocol.Decoder;
import org.redisson.client.protocol.Encoder;
import org.roaringbitmap.RoaringBitmap;
import org.roaringbitmap.buffer.ImmutableRoaringBitmap;

/**
 * @author li.bowei
 */
public class RoaringBitMapCodec extends BaseCodec {

	private final Decoder<Object> decoder = (buf, state) -> {
		final ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer();
		byteBuf.writeBytes(buf);
		byteBuf.slice();
		ImmutableRoaringBitmap bitmap = new ImmutableRoaringBitmap(byteBuf.nioBuffer());
		return new RoaringBitmap(bitmap);
	};

	private final Encoder encoder = in -> {
		RoaringBitmap bitmap = (RoaringBitmap) in;
		final ByteBuffer byteBuffer = ByteBuffer.allocateDirect(bitmap.serializedSizeInBytes());
		bitmap.serialize(byteBuffer);
		byteBuffer.flip();
		return PooledByteBufAllocator.DEFAULT.directBuffer().writeBytes(byteBuffer);
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
