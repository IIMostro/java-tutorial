package org.ilmostro.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author li.bowei on 2019-10-08.
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AbstractMapServiceTest {

    @Autowired
    @Qualifier("oneMapServiceImpl")
    private AbstractMapService service;

    @Test
    public void test1() {
        String test = service.test();
        System.out.println(test);
    }
}