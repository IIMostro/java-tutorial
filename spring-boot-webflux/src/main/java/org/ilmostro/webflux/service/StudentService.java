package org.ilmostro.webflux.service;

import org.ilmostro.webflux.domain.Student;
import reactor.core.publisher.Mono;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @author li.bowei
 */
public interface StudentService {

	Mono<ServerResponse> detail(ServerRequest request);

	Mono<ServerResponse> save(ServerRequest request);

	Mono<ServerResponse> list(ServerRequest request);
}
