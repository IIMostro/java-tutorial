package org.ilmostro.security.service;

import org.ilmostro.security.domain.UserEntity;

/**
 * @author li.bowei
 **/
public interface UserAuthenticationService {

    UserEntity save(UserEntity user);

    UserEntity findUserById(Long uid);

    UserEntity findUserByUserName(String username);

    UserEntity getCurrentUser();
}
