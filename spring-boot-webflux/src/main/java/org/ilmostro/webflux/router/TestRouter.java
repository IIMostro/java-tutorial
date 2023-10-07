package org.ilmostro.webflux.router;

import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

public class TestRouter {

    @Bean
    public RouterFunction<ServerResponse> testRouterFunction() {
        return RouterFunctions.route(GET("/test").and(accept(MediaType.APPLICATION_JSON)), v1 -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue("test"));
    }
}
