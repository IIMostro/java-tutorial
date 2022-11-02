package org.ilmostro.pure.support;

import java.lang.annotation.Annotation;

/**
 * @author li.bowei
 */
public interface EnhanceSupport {

	Class<?> enhance(Class<?> clazz, ClassLoader classLoader, Class<? extends Annotation> annotation, Class<?> proxy);
}
