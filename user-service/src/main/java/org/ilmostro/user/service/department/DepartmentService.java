package org.ilmostro.user.service.department;

import org.ilmostro.user.domain.department.Department;
import org.springframework.data.domain.Page;

/**
 * @author IlMostro
 * @date 2019-11-05 1:47
 */
public interface DepartmentService {

    Department save(Department department);

    Page<Department> queryAll(Integer page, Integer size);

    Department detail(Long id);

    Department delete(Long id);
}
