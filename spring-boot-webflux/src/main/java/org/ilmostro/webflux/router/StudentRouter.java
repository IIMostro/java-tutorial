package org.ilmostro.webflux.router;

import org.ilmostro.webflux.service.StudentService;
import org.ilmostro.webflux.service.UserService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

/**
 * @author li.bowei
 */
@Configuration
public class StudentRouter {

	private final StudentService service;

	public StudentRouter(StudentService service) {
		this.service = service;
	}

	@Bean
	public RouterFunction<ServerResponse> student() {
		return RouterFunctions
				.route(GET("/student").and(accept(MediaType.APPLICATION_JSON)), service::list)
				.andRoute(POST("/student").and(accept(MediaType.APPLICATION_JSON)), service::save);
	}
}
