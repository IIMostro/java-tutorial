package org.ilmostro.vertx.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

import java.util.concurrent.CyclicBarrier;


public class SimpleReceiveVerticle extends AbstractVerticle {

  @Override
  public void start() throws Exception {
    System.out.println("start receive:" + Thread.currentThread().getName());
    vertx.eventBus().consumer("org.ilmostro.message", handler -> System.out.println(Thread.currentThread().getName()));
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    super.start(startPromise);
  }

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(SimpleReceiveVerticle.class, new DeploymentOptions().setWorker(true).setWorkerPoolSize(40), handler ->{
      if(handler.succeeded()){
        System.out.println("deploy success!");
        CyclicBarrier barrier = new CyclicBarrier(100000);
        for (int i = 0; i < 100000; i++) {
          vertx.eventBus().request("org.ilmostro.message", "1");
        }
      }
    });
    vertx.close();
  }
}
