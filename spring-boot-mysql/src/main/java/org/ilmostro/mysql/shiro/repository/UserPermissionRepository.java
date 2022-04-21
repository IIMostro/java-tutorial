package org.ilmostro.mysql.shiro.repository;

import java.util.List;


import org.ilmostro.mysql.shiro.domain.UserPermission;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author li.bowei
 **/
public interface UserPermissionRepository extends JpaRepository<UserPermission, Long> {

    List<UserPermission> queryAllByUid(Long uid);
}
