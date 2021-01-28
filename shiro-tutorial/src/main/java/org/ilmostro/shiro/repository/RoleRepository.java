package org.ilmostro.shiro.repository;

import org.ilmostro.shiro.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author li.bowei
 **/
public interface RoleRepository extends JpaRepository<Role, Long> {
}
