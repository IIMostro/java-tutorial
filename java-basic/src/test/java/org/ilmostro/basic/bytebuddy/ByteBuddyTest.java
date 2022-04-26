package org.ilmostro.basic.bytebuddy;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.NamingStrategy;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import org.junit.Test;

/**
 * @author li.bowei
 */
@Slf4j
public class ByteBuddyTest {

	@Test
	public void create() {
		DynamicType.Unloaded<Object> make = new ByteBuddy().subclass(Object.class).make();
		log.info("make: {}", make.toString());
	}

	@Test
	public void withName() {
		DynamicType.Unloaded<Object> buddy = new ByteBuddy().with(new NamingStrategy.PrefixingRandom("org.ilmostro"))
				.subclass(Object.class).make();
		log.info("PrefixingRandom:{}", buddy);

		buddy = new ByteBuddy().with(new NamingStrategy.AbstractBase() {
			@Override
			protected String name(TypeDescription typeDescription) {
				return "org.neptune." + typeDescription.getSimpleName();
			}
		}).subclass(Object.class).make();
		log.info("AbstractBase: {}", buddy);
	}

	@Test
	public void reload(){
		Class<?> clazz = new ByteBuddy()
				.with(new NamingStrategy.AbstractBase() {
					@Override
					protected String name(TypeDescription typeDescription) {
						return "org.neptune." + typeDescription.getSimpleName();
					}
				})
				.subclass(Object.class)
				.make()
				.load(getClass().getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
				.getLoaded();
		log.info("reload class:{}", clazz);
	}

}
