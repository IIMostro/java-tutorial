package org.ilmostro.pure.support;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.reflect.MethodUtils;

/**
 * @author li.bowei
 */
public class MethodEnhanceClassSupport implements EnhanceSupport {

	@Override
	public Class<?> enhance(Class<?> clazz, ClassLoader classLoader, Class<? extends Annotation> annotation, Class<?> proxy) {
		final List<Method> methods = MethodUtils.getMethodsListWithAnnotation(clazz, annotation);
		if (CollectionUtils.isEmpty(methods)) return null;
		return new ByteBuddy()
				.subclass(clazz)
				.method(ElementMatchers.isAnnotatedWith(annotation))
//				.method(ElementMatchers.not(ElementMatchers.isDeclaredBy(Object.class)))
				.intercept(MethodDelegation.to(proxy))
				.make()
				.load(classLoader, ClassLoadingStrategy.Default.INJECTION)
				.getLoaded();
	}
}
