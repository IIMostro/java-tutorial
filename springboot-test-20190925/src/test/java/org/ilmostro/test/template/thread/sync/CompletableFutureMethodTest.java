package org.ilmostro.test.template.thread.sync;

import org.ilmostro.test.service.SimulationRemoteService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.Executor;

/**
 * @author li.bowei on 2019-10-12.
 * @description
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CompletableFutureMethodTest {

    @Autowired
    @Qualifier("applicationCustomThread")
    private Executor executor;

    private CompletableFutureMethod method;

    @Before
    public void before(){
        SimulationRemoteService service = new SimulationRemoteService();
        this.method = new CompletableFutureMethod(executor, service);
    }

    @Test
    public void simple() {
        method.simple();
    }
}