package org.ilmostro.basic.datastruct.bit;

import com.esotericsoftware.kryo.kryo5.Kryo;
import lombok.extern.slf4j.Slf4j;
import org.ilmostro.basic.datastruct.bitmap.KyroPool;
import org.ilmostro.basic.datastruct.bitmap.KyroSerializer;
import org.junit.jupiter.api.Test;
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
  public void serializer() {
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
