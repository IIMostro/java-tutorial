package org.ilmostro.basic.clazz.classloader;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.ByteBuddy;

/**
 * @author li.bowei
 */
@Slf4j
public class IsoClassMain {

	public static void main(String[] args) throws Exception{
		final ConstantClass constantClass = new ConstantClass();
		log.info("default class loader:[{}]", constantClass.getClass().getClassLoader());
		final ConstantClass loader = new ByteBuddy()
				.subclass(ConstantClass.class)
				.name(ConstantClass.class.getName())
				.make()
				.load(new IsoClassLoader())
				.getLoaded()
				.getConstructor()
				.newInstance();
		log.info("after class loader:[{}]", loader.getClass().getClassLoader());
	}
}
