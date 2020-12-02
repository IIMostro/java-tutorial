package org.ilmostro.webflux.service;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author li.bowei
 * @date 2020/8/13 15:21
 */
public interface UserService {

    /**
     * 保存一个用户
     *
     * @param request 请求参数
     * @return 添加之后的用户信息
     */
    Mono<ServerResponse> save(ServerRequest request);

    Mono<ServerResponse> list(ServerRequest request);
}
