package org.ilmostro.shiro.services;

import org.ilmostro.shiro.domain.UserEntity;

/**
 * @author li.bowei
 **/
public interface UserAuthenticationService {

    UserEntity save(UserEntity user);

    UserEntity findUserById(Long uid);

    UserEntity findUserByUserName(String username);

    UserEntity getCurrentUser();
}
