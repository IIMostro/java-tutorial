package org.ilmostro.security.repository;

import org.ilmostro.security.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author li.bowei
 **/
public interface RoleRepository extends JpaRepository<Role, Long> {
}
