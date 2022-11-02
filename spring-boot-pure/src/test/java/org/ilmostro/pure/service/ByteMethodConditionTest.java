package org.ilmostro.pure.service;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;
import org.ilmostro.pure.annotation.Logger;
import org.ilmostro.pure.service.impl.HelloServiceImpl;
import org.junit.Test;

/**
 * @author li.bowei
 */
public class ByteMethodConditionTest {


	@Test
	public void condition(){
		final DynamicType.Builder<HelloServiceImpl> subclass = new ByteBuddy().subclass(HelloServiceImpl.class);

		final ElementMatcher.Junction<MethodDescription> and = ElementMatchers.is(ElementMatchers.hasAnnotation(ElementMatchers.annotationType(Logger.class)))
				.and(MethodDescription::isPublic);
		final DynamicType.Builder.MethodDefinition.ImplementationDefinition<HelloServiceImpl> method = subclass.method(and);
	}
}
