package org.ilmostro.security.service;

import org.ilmostro.security.domain.UserEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author li.bowei
 **/
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserAuthenticationService userService;

    public UserDetailServiceImpl(UserAuthenticationService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserEntity user = userService.findUserByUserName(s);
        return new User(user.getUserName(), user.getPassword(), user.getRoles());
    }
}
