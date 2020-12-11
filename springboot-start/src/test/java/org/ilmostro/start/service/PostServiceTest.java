package org.ilmostro.start.service;

import org.ilmostro.start.service.condition.PostService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author li.bowei
 * @date 2020/10/29 16:54
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PostServiceTest {

    @Autowired
    public PostService service;

    @Test
    public void say(){
        service.sey();
    }
}