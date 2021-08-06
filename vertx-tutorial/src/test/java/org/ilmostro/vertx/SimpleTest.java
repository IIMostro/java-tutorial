package org.ilmostro.vertx;

import io.vertx.core.*;
import io.vertx.core.eventbus.EventBus;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;


public class SimpleTest {

  private static final Vertx parent = Vertx.vertx();

  @Test
  public void text() throws InterruptedException {

    parent.deployVerticle(new AbstractVerticle() {
      @Override
      public void start() {
        vertx.setTimer(500, handler -> System.out.println("delay"));
        EventBus eventBus = vertx.eventBus();
        eventBus.consumer("ilmostro", handler -> System.out.println("receive1:" + handler.body()));
        eventBus.consumer("ilmostro", handler -> System.out.println("receive2:" + handler.body()));
        eventBus.consumer("other", handler -> System.out.println("receive other:" + handler.body()));
        eventBus.publish("ilmostro", "ssss");


        vertx.executeBlocking((Handler<Promise<String>>) event -> event.complete("a"), handler ->{
          System.out.println(handler.result());
        });
      }
    });

    TimeUnit.SECONDS.sleep(2);
  }
}
