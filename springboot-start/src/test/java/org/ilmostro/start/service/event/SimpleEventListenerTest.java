package org.ilmostro.start.service.event;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

/**
 * @author li.bowei
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class SimpleEventListenerTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void event(){
        String semaphore = UUID.randomUUID().toString();
        SimpleEvent simpleEvent = new SimpleEvent(this, semaphore);
        applicationContext.publishEvent(simpleEvent);
    }
}