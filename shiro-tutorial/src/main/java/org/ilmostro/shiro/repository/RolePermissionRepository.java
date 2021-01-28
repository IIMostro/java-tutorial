package org.ilmostro.shiro.repository;

import org.ilmostro.shiro.domain.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author li.bowei
 **/
public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {

    List<RolePermission> queryAllByRoleId(Long roleId);
}
