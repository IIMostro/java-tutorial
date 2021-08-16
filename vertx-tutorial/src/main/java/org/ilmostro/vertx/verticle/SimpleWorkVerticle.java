package org.ilmostro.vertx.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class SimpleWorkVerticle extends AbstractVerticle {

  private static final Logger logger = LoggerFactory.getLogger(SimpleWorkVerticle.class);

  @Override
  public void start() throws Exception {

    Promise<Void> promise = Promise.promise();
    vertx.eventBus().consumer("ilmostro", handler -> {
      logger.info("thread{} : body{}", Thread.currentThread().getName(), handler.body());
      try {
        TimeUnit.MILLISECONDS.sleep(50);
        promise.complete();
      } catch (InterruptedException e) {
        promise.fail(e);
      }
    });
    promise.future();
  }

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    DeploymentOptions options = new DeploymentOptions().setWorker(true).setWorkerPoolSize(20);
    vertx.deployVerticle(SimpleWorkVerticle.class, options, handler -> {
      if (handler.succeeded()) {
        for (int i = 0; i < 100000; i++) {
          vertx.eventBus().send("ilmostro", String.valueOf(i));
        }
      }
    });
  }
}
