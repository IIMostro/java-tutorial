package org.ilmostro.basic.guava;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EventBusCenterTest {

  @BeforeEach
  public void before() {
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
