package org.ilmostro.mysql.shiro.repository;

import java.util.Optional;


import org.ilmostro.mysql.shiro.domain.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author li.bowei
 **/
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> queryByUserName(String username);
}
