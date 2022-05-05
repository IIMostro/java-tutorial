package org.ilmostro.basic.bit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import com.esotericsoftware.kryo.kryo5.Kryo;
import com.esotericsoftware.kryo.kryo5.io.ByteBufferInput;
import com.esotericsoftware.kryo.kryo5.io.Input;
import com.esotericsoftware.kryo.kryo5.io.Output;
import com.google.common.base.Charsets;
import lombok.extern.slf4j.Slf4j;
import org.ilmostro.basic.bitmap.KyroPool;
import org.ilmostro.basic.bitmap.KyroSerializer;
import org.ilmostro.basic.bitmap.RoaringSerializer;
import org.junit.Test;
import org.roaringbitmap.RoaringBitmap;

/**
 * @author li.bowei
 */
@Slf4j
public class RoaringBitMapTest {

	@Test
	public void test() {
		RoaringBitmap bitmap = RoaringBitmap.bitmapOf(1, 1000);
		RoaringBitmap rb2 = new RoaringBitmap();
		log.info("{}", bitmap.contains(1));
		log.info("bitmap: {}", bitmap);
		log.info("bitmap r2: {}", rb2);
	}

	@Test
	public void serializer(){
		RoaringBitmap bitmap = RoaringBitmap.bitmapOf(1, 1000);
		KyroPool pool = KyroPool.getInstance();
		Kryo kryo = pool.obtain();
		byte[] serialize = KyroSerializer.serialize(bitmap);
		log.info("serialize value:{}", serialize);
		RoaringBitmap deserialize = KyroSerializer.deserialize(serialize, RoaringBitmap.class);
		log.info("deserialize value:{}", deserialize);
		pool.free(kryo);
	}

}
