package org.ilmostro.shiro.repository;

import org.ilmostro.shiro.domain.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author li.bowei
 **/
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    List<UserRole> queryAllByUserId(Long uid);
}
