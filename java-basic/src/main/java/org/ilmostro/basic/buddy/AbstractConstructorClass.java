package org.ilmostro.basic.buddy;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author li.bowei
 */
public abstract class AbstractConstructorClass {

	private static final String name;
	private final String clazz;

	static{
		name = "hello world";
	}

	public AbstractConstructorClass(String clazz) {
		this.clazz = clazz;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("clazz", clazz)
				.append("name", name)
				.toString();
	}
}
