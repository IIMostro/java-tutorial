package org.ilmostro.pure.utils;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import org.springframework.util.StopWatch;

/**
 * @author li.bowei
 */
public class StopWatchTests {

	@Test
	void test() throws Exception{
		StopWatch watch = new StopWatch();
		watch.start("1");
		TimeUnit.MILLISECONDS.sleep(100);
		watch.stop();
		watch.start("2");
		TimeUnit.MILLISECONDS.sleep(100);
		watch.stop();
		watch.start("3");
		TimeUnit.MILLISECONDS.sleep(100);
		watch.stop();
		System.out.println(watch.prettyPrint());
	}
}
