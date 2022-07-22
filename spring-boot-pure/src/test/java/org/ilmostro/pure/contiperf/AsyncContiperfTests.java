package org.ilmostro.pure.contiperf;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author li.bowei
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class AsyncContiperfTests {

	@Autowired
	private WebApplicationContext context;

	@Rule
	public ContiPerfRule contiPerfRule = new ContiPerfRule();

	private MockMvc mockMvc;

	@Before
	public void before() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	@PerfTest(invocations = 100, threads = 10)
	public void test() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/async/second"));
	}
}
