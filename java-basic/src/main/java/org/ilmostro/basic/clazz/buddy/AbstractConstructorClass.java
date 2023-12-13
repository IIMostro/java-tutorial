package org.ilmostro.basic.clazz.buddy;

import lombok.ToString;

/**
 * @author li.bowei
 */
@ToString
public abstract class AbstractConstructorClass {

	private static final String name;
	private final String clazz;

	static{
		name = "hello world";
	}

	public AbstractConstructorClass(String clazz) {
		this.clazz = clazz;
	}
}
