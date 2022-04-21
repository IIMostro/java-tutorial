package org.ilmostro.mysql.shiro.repository;

import java.util.List;


import org.ilmostro.mysql.shiro.domain.UserRole;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author li.bowei
 **/
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    List<UserRole> queryAllByUserId(Long uid);
}
