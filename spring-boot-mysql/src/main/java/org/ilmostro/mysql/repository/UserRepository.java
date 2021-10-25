package org.ilmostro.mysql.repository;

import org.ilmostro.mysql.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author li.bowei
 */
public interface UserRepository extends JpaRepository<User, Integer> {
}
