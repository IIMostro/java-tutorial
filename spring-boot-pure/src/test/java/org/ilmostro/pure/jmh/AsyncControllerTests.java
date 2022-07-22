package org.ilmostro.pure.jmh;

import java.util.concurrent.TimeUnit;

import org.ilmostro.pure.PureApplication;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author li.bowei
 */
// 基准测试的模式
@BenchmarkMode(Mode.All)
// 输出时间
@OutputTimeUnit(TimeUnit.MILLISECONDS)
// 示例可用范围
@State(Scope.Thread)
public class AsyncControllerTests {


	private MockMvc mockMvc;

	@Setup(Level.Trial)
	public void init(){
		ConfigurableApplicationContext context = SpringApplication.run(PureApplication.class);
		this.mockMvc = MockMvcBuilders.webAppContextSetup((WebApplicationContext) context).build();
	}

	@Benchmark
	public void test() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.post("/async/first"));
	}
}
