package org.ilmostro.webflux.repository;

import org.ilmostro.webflux.domain.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * @author li.bowei
 * @date 2020/8/13 15:20
 */
public interface UserRepository extends ReactiveMongoRepository<User, Long> {
}
