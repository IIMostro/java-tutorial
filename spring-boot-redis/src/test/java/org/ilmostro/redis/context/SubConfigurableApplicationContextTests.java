package org.ilmostro.redis.context;

import org.ilmostro.redis.RedisApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootTest(classes = RedisApplication.class)
public class SubConfigurableApplicationContextTests {

    @Test
    void test_refresh_context_should_work() {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(RedisAutoConfiguration.class)
            .web(WebApplicationType.NONE)
            .run();
        context.refresh();
    }
}
