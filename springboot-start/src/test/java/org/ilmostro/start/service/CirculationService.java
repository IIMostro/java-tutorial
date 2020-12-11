package org.ilmostro.start.service;

import org.ilmostro.start.service.condition.Circulation1;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author li.bowei
 * @date 2020/9/30 13:40
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CirculationService {

    @Autowired
    private Circulation1 service;

    @Test
    public void say(){
        service.say();
    }
}
