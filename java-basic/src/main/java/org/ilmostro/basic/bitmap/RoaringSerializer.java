package org.ilmostro.basic.bitmap;

import java.io.IOException;

import com.esotericsoftware.kryo.kryo5.Kryo;
import com.esotericsoftware.kryo.kryo5.Serializer;
import com.esotericsoftware.kryo.kryo5.io.Input;
import com.esotericsoftware.kryo.kryo5.io.KryoDataInput;
import com.esotericsoftware.kryo.kryo5.io.KryoDataOutput;
import com.esotericsoftware.kryo.kryo5.io.Output;
import org.roaringbitmap.RoaringBitmap;

/**
 * @author li.bowei
 */
public class RoaringSerializer extends Serializer<RoaringBitmap> {
	@Override
	public void write(Kryo kryo, Output output, RoaringBitmap bitmap) {
		try {
			bitmap.serialize(new KryoDataOutput(output));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public RoaringBitmap read(Kryo kryo, Input input, Class<? extends RoaringBitmap> aClass) {
		RoaringBitmap bitmap = new RoaringBitmap();
		try {
			bitmap.deserialize(new KryoDataInput(input));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return bitmap;
	}
}
