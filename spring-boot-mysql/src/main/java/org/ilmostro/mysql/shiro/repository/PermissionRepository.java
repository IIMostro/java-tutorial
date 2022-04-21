package org.ilmostro.mysql.shiro.repository;


import org.ilmostro.mysql.shiro.domain.Permission;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author li.bowei
 **/
public interface PermissionRepository extends JpaRepository<Permission, Long> {

}
