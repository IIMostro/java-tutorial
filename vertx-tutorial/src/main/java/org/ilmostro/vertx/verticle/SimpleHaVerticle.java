package org.ilmostro.vertx.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

public class SimpleHaVerticle extends AbstractVerticle {

  @Override
  public void start() {
    System.out.println("deploy");
    vertx.eventBus().consumer("ilmostro", handler -> System.out.println(Thread.currentThread().getName() + "receive: " + handler.body()));
  }

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    Promise<Object> promise = Promise.promise();
    vertx.deployVerticle(SimpleHaVerticle.class, new DeploymentOptions().setWorkerPoolSize(5),
      handler -> vertx.setPeriodic(1, time -> vertx.eventBus().publish("ilmostro", "h1")));
  }
}
