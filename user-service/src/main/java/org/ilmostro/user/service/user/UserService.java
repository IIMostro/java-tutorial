package org.ilmostro.user.service.user;

import org.ilmostro.user.domain.user.User;
import org.springframework.data.domain.Page;

/**
 * @author li.bowei on 2019-10-29.
 * @description
 */
public interface UserService {

    User save(User user);

    Page<User> queryAll(Integer page, Integer size);

    User detail(Long uid);

    User delete(Long uid);

    Page<User> queryByDepartment(Long department, Integer page, Integer size);
}
