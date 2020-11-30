package org.ilmostro.user.repository.user;

import org.ilmostro.user.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author li.bowei on 2019-10-29.
 * @description
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> queryUsersByDepartment_Id(Long department, Pageable pageable);
}
