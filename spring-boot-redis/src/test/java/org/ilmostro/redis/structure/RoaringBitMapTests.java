package org.ilmostro.redis.structure;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.nio.ByteBuffer;

import com.esotericsoftware.kryo.kryo5.io.Output;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import lombok.extern.slf4j.Slf4j;
import org.ilmostro.redis.utils.KyroPool;
import org.junit.jupiter.api.Test;
import org.redisson.api.RBinaryStream;
import org.redisson.api.RedissonClient;
import org.roaringbitmap.RoaringBitmap;
import org.roaringbitmap.buffer.ImmutableRoaringBitmap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author li.bowei
 */
@SpringBootTest
@Slf4j
public class RoaringBitMapTests {

	@Autowired
	private RedissonClient redisson;

	private static final String KEY = "roaring-bit-map-test";

	@Test
	void test() throws Exception {
		final RoaringBitmap bitmap = RoaringBitmap.bitmapOf(1, 50, 100);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		bitmap.serialize(dos);
		final RBinaryStream stream = redisson.getBinaryStream(KEY);
		stream.set(bos.toByteArray());
	}

	@Test
	void read() {
		final RBinaryStream stream = redisson.getBinaryStream(KEY);
		final ByteBuffer buffer = ByteBuffer.wrap(stream.get());
		ImmutableRoaringBitmap bitmap = new ImmutableRoaringBitmap(buffer);
		log.info("bitmap:[{}]", new RoaringBitmap(bitmap));
	}

	@Test
	void kyroWrite() {
		final RoaringBitmap bitmap = RoaringBitmap.bitmapOf(1, 50, 100);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		final Output output = new Output(bos);
		KyroPool.getInstance().obtain().writeClassAndObject(output, bitmap);
		output.flush();
		final RBinaryStream stream = redisson.getBinaryStream(KEY);
		stream.set(bos.toByteArray());
	}

	@Test
	void kyroRead() {
		final RBinaryStream stream = redisson.getBinaryStream(KEY);
		final ByteBuffer buffer = ByteBuffer.wrap(stream.get());
		ImmutableRoaringBitmap bitmap = new ImmutableRoaringBitmap(buffer);
		log.info("kyro read bitmap:[{}]", bitmap);
	}

	@Test
	void byteBuf() {
		final RoaringBitmap bitmap = RoaringBitmap.bitmapOf(1, 50, 100);
		final ByteBuffer byteBuffer = ByteBuffer.allocateDirect(bitmap.serializedSizeInBytes());
		bitmap.serialize(byteBuffer);
		final RBinaryStream stream = redisson.getBinaryStream(KEY);
		byteBuffer.flip();
		final byte[] bytes = new byte[byteBuffer.remaining()];
		byteBuffer.get(bytes, 0, bytes.length);
		stream.set(bytes);
	}

	@Test
	void byteBufRead() {
		final RBinaryStream stream = redisson.getBinaryStream(KEY);
		final byte[] bytes = stream.get();
		final ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer();
		byteBuf.writeBytes(bytes);
		byteBuf.slice();
		ImmutableRoaringBitmap bitmap = new ImmutableRoaringBitmap(byteBuf.nioBuffer());
		log.info("kyro read bitmap:[{}]", new RoaringBitmap(bitmap));
	}

	@Test
	void byteBufV1() {
		final RoaringBitmap bitmap = RoaringBitmap.bitmapOf(1, 50, 100);
		final ByteArrayOutputStream baos = new ByteArrayOutputStream(bitmap.serializedSizeInBytes());
		final Output output = new Output(baos);
		KyroPool.getInstance().obtain().writeClassAndObject(output, bitmap);
		final RBinaryStream stream = redisson.getBinaryStream(KEY);
		output.flush();
		stream.set(baos.toByteArray());
	}
}
