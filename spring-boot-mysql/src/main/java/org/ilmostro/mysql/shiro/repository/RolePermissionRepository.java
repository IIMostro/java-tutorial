package org.ilmostro.mysql.shiro.repository;

import java.util.List;


import org.ilmostro.mysql.shiro.domain.RolePermission;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author li.bowei
 **/
public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {

    List<RolePermission> queryAllByRoleId(Long roleId);
}
