package org.ilmostro.user.repository.department;

import org.ilmostro.user.domain.department.Department;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author li.bowei on 2019-10-29.
 * @description
 */
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
