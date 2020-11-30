package org.ilmostro.user.service.department;

import lombok.extern.slf4j.Slf4j;
import org.ilmostro.user.domain.department.Department;
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
 * @date 2019-11-05 20:45
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DepartmentServiceTest {

    @Autowired
    private DepartmentService service;

    @Test
    public void queryAll(){
        Page<Department> data = service.queryAll(0, 1);
        Optional.ofNullable(data)
                .map(Slice::getContent)
                .orElse(Collections.emptyList())
                .forEach(var1 -> log.info("department {}", var1));
    }

    @Test
    public void detail(){
        Department detail = service.detail(1L);
        log.info("detail {}", detail);
    }
}
