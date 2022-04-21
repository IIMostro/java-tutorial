package org.ilmostro.basic.bit;

import com.esotericsoftware.kryo.kryo5.Kryo;
import com.esotericsoftware.kryo.kryo5.io.Output;
import com.google.common.base.Charsets;
import lombok.extern.slf4j.Slf4j;
import org.ilmostro.basic.bitmap.KyroPool;
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
		Output output = new Output(1024, -1);
		Kryo kryo = pool.obtain();
		kryo.writeObject(output, bitmap, new RoaringSerializer());
		log.info("serializer value:{}", new String(output.getBuffer(), Charsets.UTF_8));
		pool.free(kryo);
	}

}
