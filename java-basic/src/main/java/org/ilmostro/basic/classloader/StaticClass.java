package org.ilmostro.basic.classloader;

import java.util.function.Consumer;

import lombok.extern.slf4j.Slf4j;

/**
 * @author li.bowei
 */
@Slf4j
public class StaticClass {

	static{
		log.info("this is static block");
	}

	public static final Consumer<String> staticFunction = v1 -> log.info("this is static function!");

	public static void staticMethod(){
		log.info("this is static method");
	}
}
