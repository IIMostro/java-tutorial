package org.ilmostro.start.service;

import org.ilmostro.start.service.aspect.SecondAspectServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author li.bowei
 * @date 2020/10/29 16:11
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DefaultServiceImplTest {

    @Autowired
    private SecondAspectServiceImpl service;

    @Test
    public void say(){
        service.say();
        service.simple();
    }
}