package org.ilmostro.basic.classloader;

import org.ilmostro.basic.clazz.classloader.StaticClass;
import org.junit.Test;

/**
 * @author li.bowei
 */
public class StaticClassTests {

	@Test
	public void test(){
		StaticClass.staticFunction.accept("1");
	}

}
