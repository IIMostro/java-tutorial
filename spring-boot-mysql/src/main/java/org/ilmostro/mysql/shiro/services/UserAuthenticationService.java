package org.ilmostro.mysql.shiro.services;


import org.ilmostro.mysql.shiro.domain.UserEntity;

/**
 * @author li.bowei
 **/
public interface UserAuthenticationService {

    UserEntity save(UserEntity user);

    UserEntity findUserById(Long uid);

    UserEntity findUserByUserName(String username);

    UserEntity getCurrentUser();
}
