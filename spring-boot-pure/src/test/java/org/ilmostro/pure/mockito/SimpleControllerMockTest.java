package org.ilmostro.pure.mockito;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author li.bowei
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class SimpleControllerMockTest {

	@Autowired
	private WebApplicationContext applicationContext;

	private MockMvc mockMvc;

	@Before
	public void before(){
		mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
	}

	@Test
	public void now() throws Exception{
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/simple/now")
				.accept(MediaType.APPLICATION_JSON)
				.param("name", "ilmostro")
		).andReturn();

		assertEquals(200, result.getResponse().getStatus());
		assertEquals("ilmostro", result.getResponse().getContentAsString());
	}

}
