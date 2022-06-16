package org.ilmostro.redis.utils;

import com.esotericsoftware.kryo.kryo5.Kryo;
import com.esotericsoftware.kryo.kryo5.util.Pool;
import org.roaringbitmap.RoaringBitmap;

/**
 * @author li.bowei
 */
public class KyroPool extends Pool<Kryo> {

	public static KyroPool getInstance() {
		return LazyHolder.pool;
	}

	private static class LazyHolder{
		private static final KyroPool pool = new KyroPool(true, false);
	}

	public KyroPool(boolean threadSafe, boolean softReferences) {
		super(threadSafe, softReferences);
	}

	@Override
	protected Kryo create() {
		Kryo kryo = new Kryo();
		// 关闭序列化注册，会导致性能些许下降，但在分布式环境中，注册类生成ID不一致会导致错误
		kryo.setRegistrationRequired(false);
		// 支持循环引用，也会导致性能些许下降 T_T
		kryo.setReferences(false);
		kryo.register(RoaringBitmap.class, new RoaringSerializer(), 11);
		return kryo;
	}
}
