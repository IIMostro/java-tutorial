package org.ilmostro.shiro.repository;

import org.ilmostro.shiro.domain.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author li.bowei
 **/
public interface PermissionRepository extends JpaRepository<Permission, Long> {

}
