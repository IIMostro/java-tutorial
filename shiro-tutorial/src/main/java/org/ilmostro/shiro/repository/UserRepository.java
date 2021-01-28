package org.ilmostro.shiro.repository;

import org.ilmostro.shiro.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author li.bowei
 **/
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> queryByUserName(String username);
}
