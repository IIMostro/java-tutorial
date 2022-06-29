package org.ilmostro.basic.bytebuddy;

import java.lang.reflect.Constructor;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.NamingStrategy;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.pool.TypePool;
import org.ilmostro.basic.buddy.AbstractConstructorClass;
import org.ilmostro.basic.buddy.ConstructorClass;
import org.junit.Test;

/**
 * @author li.bowei
 */
@Slf4j
public class ByteBuddyTest {

	@Test
	public void create() throws Exception {
		DynamicType.Unloaded<Object> make = new ByteBuddy().subclass(Object.class).make();
		log.info("make: {}", make.toString());

		final Class<? extends ConstructorClass> init = new ByteBuddy()
				.subclass(ConstructorClass.class)
				.method(ElementMatchers.named("getCustomName"))
				.intercept(FixedValue.value("Hello World!"))
				.make()
				.load(getClass().getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
				.getLoaded();
		final Constructor<? extends ConstructorClass> constructor = init.getConstructor(String.class);
		final ConstructorClass newInstance = constructor.newInstance("hello world");
		log.info("make init:[{}]", newInstance.getCustomName());

		final Class<? extends AbstractConstructorClass> loaded = new ByteBuddy()
				.subclass(AbstractConstructorClass.class)
				.field(ElementMatchers.definedField(ElementMatchers.named("name")))
				.value("hello")
				.make()
				.load(getClass().getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
				.getLoaded();

		final Constructor<? extends AbstractConstructorClass> declaredConstructor = loaded
				.getDeclaredConstructor(String.class);
		declaredConstructor.setAccessible(true);
		final AbstractConstructorClass abstractConstructorClass = declaredConstructor.newInstance("hello");
		log.info("loaded:[{}]", abstractConstructorClass.toString());
	}

	@Test
	public void field(){

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
