package org.ilmostro.security.service;

import lombok.extern.slf4j.Slf4j;
import org.ilmostro.security.domain.Role;
import org.ilmostro.security.domain.UserEntity;
import org.ilmostro.security.domain.UserRole;
import org.ilmostro.security.repository.RoleRepository;
import org.ilmostro.security.repository.UserRepository;
import org.ilmostro.security.repository.UserRoleRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author li.bowei
 **/
@Service
@Slf4j
public class DefaultUserAuthenticationServiceImpl implements UserAuthenticationService {

    private final UserRepository repository;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;

    public DefaultUserAuthenticationServiceImpl(UserRepository repository,
                                                UserRoleRepository userRoleRepository,
                                                RoleRepository roleRepository) {
        this.repository = repository;
        this.userRoleRepository = userRoleRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserEntity save(UserEntity user) {
        if(Objects.isNull(user)){
            log.error("save user error! user is null");
            throw new IllegalArgumentException();
        }
        return repository.save(user);
    }

    @Override
    public UserEntity findUserById(Long uid) {
        return repository.findById(uid).map(this::enhance).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public UserEntity findUserByUserName(String username) {
        return repository.queryByUserName(username).map(this::enhance).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(Objects.isNull(authentication) || Objects.isNull(authentication.getPrincipal())){
            log.error("get current user error! cause context authentication is null!");
            throw new IllegalArgumentException();
        }
        return Optional.of(authentication)
                .map(Authentication::getPrincipal)
                .map(Objects::toString)
                .map(repository::queryByUserName)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(this::enhance)
                .orElse(null);
    }

    private UserEntity enhance(UserEntity user){
        List<Long> collect = Optional.of(user)
                .map(UserEntity::getId)
                .map(userRoleRepository::queryAllByUserId)
                .orElseGet(Collections::emptyList)
                .stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toList());
        List<Role> roles = roleRepository.findAllById(collect);
        user.setRoles(roles);
        return user;
    }
}
