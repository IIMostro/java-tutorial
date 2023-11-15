package org.ilmostro.pure.camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class SampleRouteBuilder extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("timer:hello?period=1000")
                .setBody()
                .simple("Hello World")
                .to("log:output");
    }
}
