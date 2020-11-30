package org.ilmostro.user.service.user;

import lombok.extern.slf4j.Slf4j;
import org.ilmostro.user.domain.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Optional;

/**
 * @author IlMostro
 * @date 2019-11-05 22:53
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserServiceTest {

    @Autowired
    private UserService service;

    @Test
    public void queryAll(){
        Page<User> users = service.queryAll(0, 10);
        Optional.of(users).map(Slice::getContent).orElse(Collections.emptyList()).forEach(var1 -> log.info("user {}", var1));
    }

    @Test
    public void queryByDepartment(){
        Page<User> users = service.queryByDepartment(1L, 0, 10);
        Optional.of(users).map(Slice::getContent).orElse(Collections.emptyList()).forEach(var1 -> log.info("user {}", var1));
    }

    @Test
    public void detail(){
        User detail = service.detail(1L);
        log.info("user {}", detail);
    }
}
