package org.ilmostro.webflux.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.ilmostro.webflux.domain.Student;
import org.ilmostro.webflux.repository.StudentRepository;
import org.ilmostro.webflux.service.StudentService;
import reactor.core.publisher.Mono;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @author li.bowei
 */
@Service
@Slf4j
public class DefaultStudentServiceImpl implements StudentService {

	private final StudentRepository repository;

	public DefaultStudentServiceImpl(StudentRepository repository) {
		this.repository = repository;
	}

	@Override
	public Mono<ServerResponse> detail(ServerRequest request) {
		Mono<Student> detail = request.bodyToMono(Long.class)
				.flatMap(repository::findById)
				.doOnError(ex -> log.error("save user error, cause:{}", ex.getMessage()));
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(detail, Student.class);
	}

	@Override
	public Mono<ServerResponse> save(ServerRequest request) {
		Mono<Student> studentMono = request.bodyToMono(Student.class)
				.flatMap(repository::save)
				.doOnError(ex -> log.error("save user error, cause:{}", ex.getMessage()));
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(studentMono, Student.class);
	}

	@Override
	public Mono<ServerResponse> list(ServerRequest request) {
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(repository.findAll(), Student.class);
	}
}
