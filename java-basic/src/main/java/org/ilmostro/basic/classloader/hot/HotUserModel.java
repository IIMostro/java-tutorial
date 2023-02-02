package org.ilmostro.basic.classloader.hot;

import org.slf4j.helpers.MessageFormatter;

/**
 * @author li.bowei
 */
public class HotUserModel {

	private final String name = "jack";

	private final Integer age = 18;

	public void show(){
		System.out.println(MessageFormatter.format("name:{}, age:{}", name, age));
		System.out.println(MessageFormatter.format("current classloader:[{}]", this.getClass().getClassLoader()));
	}
}
