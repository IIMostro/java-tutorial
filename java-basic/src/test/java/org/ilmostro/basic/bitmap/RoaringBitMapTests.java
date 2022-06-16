package org.ilmostro.basic.bitmap;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.roaringbitmap.RoaringBitmap;

/**
 * @author li.bowei
 */
@Slf4j
public class RoaringBitMapTests {

	@Test
	public void xor(){
		final RoaringBitmap first = RoaringBitmap.bitmapOf(1, 4, 100);
		final RoaringBitmap second = RoaringBitmap.bitmapOf(2, 4, 99);

		log.info("first:[{}]", first);
		log.info("second:[{}]", second);

		first.xor(second);
		log.info("xor after first:[{}]", first);
	}

	@Test
	public void and(){
		final RoaringBitmap first = RoaringBitmap.bitmapOf(1, 4, 100);
		final RoaringBitmap second = RoaringBitmap.bitmapOf(2, 4, 99);
		first.and(second);
		log.info("and after first:[{}]", first);
	}

	@Test
	public void or(){
		final RoaringBitmap first = RoaringBitmap.bitmapOf(1, 4, 100);
		final RoaringBitmap second = RoaringBitmap.bitmapOf(2, 4, 99);

		first.or(second);
		log.info("or after first:[{}]", first);
	}
}
