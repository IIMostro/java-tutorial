package org.ilmostro.webflux.router;

import org.ilmostro.webflux.exception.BizException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

/**
 * @author li.bowei
 * @date 2020/12/2 16:57
 */
@Configuration
public class ExceptionRouter {

    @Bean
    public RouterFunction<ServerResponse> exceptionRouterFunction() {
        return RouterFunctions.route(GET("/exception"), req -> {
         throw new BizException(1000, "user not find!");
        });
    }
}
