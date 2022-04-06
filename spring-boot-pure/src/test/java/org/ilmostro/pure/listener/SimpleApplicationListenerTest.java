package org.ilmostro.pure.listener;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author li.bowei
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SimpleApplicationListenerTest {

	@Autowired
	private ApplicationContext context;

	@Test
	public void publisher() throws Exception{
		context.publishEvent(new SimpleApplicationEvent(this, "hello!"));
	}

}
