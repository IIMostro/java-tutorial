package org.ilmostro.basic.guava;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EventBusCenterTest {

    @Before
    public void before(){
        EventBusHandler first = new FirstEventBusHandler();
        EventBusHandler second = new SecondEventBusHandler();
        EventBusHandler exception = new ExceptionEventBusHandler();
        EventBusCenter.register(first, second, exception);
    }

    @Test
    public void post() throws InterruptedException {
        EventBusCenter.post("2020年12月17日16:52:16");
        Thread.sleep(1000);
    }
}
