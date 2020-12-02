package org.ilmostro.webflux.router;

import org.ilmostro.webflux.exception.BizException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

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
