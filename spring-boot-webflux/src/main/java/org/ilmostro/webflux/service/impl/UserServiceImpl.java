package org.ilmostro.webflux.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.ilmostro.webflux.domain.User;
import org.ilmostro.webflux.repository.UserRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import org.ilmostro.webflux.service.UserService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @author li.bowei
 * @date 2020/8/13 15:22
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<ServerResponse> save(ServerRequest request) {
        Mono<User> userMono = request
                .bodyToMono(User.class)
                .flatMap(repository::save)
                .doOnError(ex -> log.error("save user error, cause:{}", ex.getMessage()));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(userMono, User.class);
    }

    @Override
    public Mono<ServerResponse> list(ServerRequest request) {
        Flux<User> all = repository.findAll().filter(var1 -> Objects.nonNull(var1.getBirthday()));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(all, User.class);
    }
}
