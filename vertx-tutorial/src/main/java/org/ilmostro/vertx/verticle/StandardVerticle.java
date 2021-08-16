package org.ilmostro.vertx.verticle;

import io.vertx.core.*;

public class StandardVerticle extends AbstractVerticle {

  @Override
  public void start() throws Exception {
    vertx.eventBus().consumer("org.ilmostro.message", handler -> System.out.println(Thread.currentThread().getName() + ":" + handler.body()));
  }

  public static void main(String[] args) throws Exception {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(StandardVerticle.class, new DeploymentOptions().setInstances(1).setWorker(true).setWorkerPoolSize(40));
//    for (int i = 0; i < 1000; i++) {
//      TimeUnit.MILLISECONDS.sleep(10);
//      vertx.eventBus().publish("org.ilmostro.message", i);
//    }
    vertx.executeBlocking(event -> {
      for (int i = 0; i < 100000; i++) {
        vertx.eventBus().publish("org.ilmostro.message", i);
      }
    }, false);
  }
}
