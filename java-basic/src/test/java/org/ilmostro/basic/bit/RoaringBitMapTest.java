package org.ilmostro.basic.bit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
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
		try(ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ByteArrayInputStream bais = new ByteArrayInputStream(new byte[1024]);
			DataInputStream dis = new DataInputStream(bais);
			DataOutputStream dos = new DataOutputStream(baos)) {
			bitmap.deserialize(dis);
			IOUtils.copy(bais, dos);
			rb2.serialize(dos);
		}catch (Exception ex){
			ex.printStackTrace();
		}
		log.info("{}", bitmap.contains(1));
		log.info("bitmap: {}", bitmap);
		log.info("bitmap r2: {}", rb2);
	}
}
