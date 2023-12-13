package org.ilmostro.basic.generic;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import lombok.extern.slf4j.Slf4j;
import org.ilmostro.basic.clazz.generic.GenericClass;
import org.junit.Test;

/**
 * @author li.bowei
 */
@Slf4j
public class GenericTest {

	@Test
	public void method(){
		Method[] methods = GenericClass.class.getMethods();
		for (Method method : methods) {
			Type[] parameter = method.getGenericParameterTypes();
			Type[] exceptions = method.getGenericExceptionTypes();
			Type returnType = method.getGenericReturnType();

			log.info("method:{}", method.getName());
			for (Type type : parameter) {
				log.info("getGenericParameterType: {}", type.getTypeName());
			}

			for (Type exception : exceptions) {
				log.info("getGenericExceptionTypes: {}", exception.getTypeName());
			}
			log.info("getGenericReturnType: {}", returnType.getTypeName());
		}
	}
}
