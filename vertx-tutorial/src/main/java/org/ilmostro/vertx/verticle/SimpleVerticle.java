package org.ilmostro.vertx.verticle;

import io.vertx.core.AbstractVerticle;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
public class SimpleVerticle extends AbstractVerticle {

  private final SimpleService service;

  public SimpleVerticle(SimpleService service) {
    this.service = service;
  }

  @Override
  public void start() throws Exception {
    System.out.println("start simple verticle: service hashcode:" + service.hashCode());
      vertx.setPeriodic(1000, handler -> {
        vertx.eventBus().publish("org.ilmostro.message", service.hello() + System.currentTimeMillis());
      });
  }
}
