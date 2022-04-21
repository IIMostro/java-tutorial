package org.ilmostro.mysql.shiro.repository;


import org.ilmostro.mysql.shiro.domain.Role;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author li.bowei
 **/
public interface RoleRepository extends JpaRepository<Role, Long> {
}
