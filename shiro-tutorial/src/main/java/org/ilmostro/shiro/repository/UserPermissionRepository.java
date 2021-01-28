package org.ilmostro.shiro.repository;

import org.ilmostro.shiro.domain.UserPermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author li.bowei
 **/
public interface UserPermissionRepository extends JpaRepository<UserPermission, Long> {

    List<UserPermission> queryAllByUid(Long uid);
}
