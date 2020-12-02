package org.ilmostro.webflux.router;

import org.ilmostro.webflux.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

/**
 * @author li.bowei
 * @date 2020/8/13 15:22
 */
@Configuration
public class UserRouter {

    private final UserService service;

    public UserRouter(UserService service) {
        this.service = service;
    }

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route(GET("/user").and(accept(MediaType.APPLICATION_JSON)), service::list)
                .andRoute(POST("/user").and(accept(MediaType.APPLICATION_JSON)), service::save);
    }
}
