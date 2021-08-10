package org.ilmostro.vertx.verticle;

import io.vertx.core.AbstractVerticle;


public class SimpleReceiveVerticle extends AbstractVerticle {

  @Override
  public void start() throws Exception {
    vertx.eventBus().consumer("org.ilmostro.message", handler -> System.out.println(handler.body()));
  }
}
