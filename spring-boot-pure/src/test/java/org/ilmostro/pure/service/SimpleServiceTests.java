package org.ilmostro.pure.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author li.bowei
 */
@SpringBootTest
@RunWith(SpringRunner.class)
class SimpleServiceTests {

	@MockBean
	private HelloService helloService;

	@Autowired
	private SimpleService service;

	@Test
	void say() {
	}

	@Test
	void get() {
		Mockito.when(helloService.hello()).thenReturn("mock");
		String s = service.get("111");
		assertEquals(s, "111mock");
	}
}