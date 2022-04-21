package org.ilmostro.mysql.shiro.services;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.ilmostro.mysql.shiro.domain.Permission;
import org.ilmostro.mysql.shiro.domain.Role;
import org.ilmostro.mysql.shiro.domain.RolePermission;
import org.ilmostro.mysql.shiro.domain.UserEntity;
import org.ilmostro.mysql.shiro.domain.UserPermission;
import org.ilmostro.mysql.shiro.domain.UserRole;
import org.ilmostro.mysql.shiro.repository.PermissionRepository;
import org.ilmostro.mysql.shiro.repository.RolePermissionRepository;
import org.ilmostro.mysql.shiro.repository.RoleRepository;
import org.ilmostro.mysql.shiro.repository.UserPermissionRepository;
import org.ilmostro.mysql.shiro.repository.UserRepository;
import org.ilmostro.mysql.shiro.repository.UserRoleRepository;

import org.springframework.stereotype.Service;

/**
 * @author li.bowei
 **/
@Service
@Slf4j
public class DefaultUserAuthenticationServiceImpl implements UserAuthenticationService {

    private final UserRepository repository;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final UserPermissionRepository userPermissionRepository;

    public DefaultUserAuthenticationServiceImpl(UserRepository repository,
                                                UserRoleRepository userRoleRepository,
                                                RoleRepository roleRepository,
                                                PermissionRepository permissionRepository,
                                                RolePermissionRepository rolePermissionRepository,
                                                UserPermissionRepository userPermissionRepository) {
        this.repository = repository;
        this.userRoleRepository = userRoleRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.rolePermissionRepository = rolePermissionRepository;
        this.userPermissionRepository = userPermissionRepository;
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
        Object principal = SecurityUtils.getSubject().getPrincipal();
        return repository.queryByUserName(principal.toString()).map(this::enhance).orElse(null);
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
        List<Long> permissionIds = roles.stream()
                .map(Role::getId)
                .map(rolePermissionRepository::queryAllByRoleId)
                .flatMap(Collection::stream)
                .map(RolePermission::getPermissionId)
                .collect(Collectors.toList());


        List<Long> collect1 = Optional.of(user)
                .map(UserEntity::getId)
                .map(userPermissionRepository::queryAllByUid)
                .orElseGet(Collections::emptyList)
                .stream()
                .map(UserPermission::getPermissionId)
                .collect(Collectors.toList());

        permissionIds.addAll(collect1);

        List<Permission> allById = permissionRepository.findAllById(permissionIds);

        user.setPermissions(allById);
        return user;
    }
}
