package org.ilmostro.pure.support;

import java.lang.annotation.Annotation;
import java.util.Objects;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

import org.springframework.core.annotation.AnnotationUtils;

/**
 * @author li.bowei
 */
public class TypeEnhanceClassSupport implements EnhanceSupport {

	@Override
	public Class<?> enhance(Class<?> clazz, ClassLoader classLoader, Class<? extends Annotation> annotation, Class<?> proxy) {
		final Annotation targetAnnotation = AnnotationUtils.findAnnotation(clazz, annotation);
		if (Objects.isNull(targetAnnotation)) return null;
		return new ByteBuddy()
				.subclass(clazz)
//				.method(ElementMatchers.isAnnotatedWith(LoggerWrapper.class))
				.method(ElementMatchers.not(ElementMatchers.isDeclaredBy(Object.class)))
				.intercept(MethodDelegation.to(proxy))
				.make()
				.load(classLoader, ClassLoadingStrategy.Default.INJECTION)
				.getLoaded();
	}
}
