package org.ilmostro.basic.clazz.buddy;

/**
 * @author li.bowei
 */
public class BizServiceImpl implements BizService {


	@Override
	public void say(String message) {
		System.out.println("message: " + message);
	}

	@Override
	public String hello(String v1, String v2) {
		System.out.println("v1: " + v1 + "v2: " + v2);
		return v1 + v2;
	}
}
