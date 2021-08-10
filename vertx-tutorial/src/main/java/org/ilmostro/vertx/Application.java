package org.ilmostro.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import org.ilmostro.vertx.configuration.VertxConfiguration;
import org.ilmostro.vertx.configuration.VertxSpringFactory;
import org.ilmostro.vertx.verticle.SimpleVerticle;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application extends AbstractVerticle {

  public static void main(String[] args) {

    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(VertxConfiguration.class);
    Vertx vertx = context.getBean(Vertx.class);
    VertxSpringFactory factory = context.getBean(VertxSpringFactory.class);
    vertx.registerVerticleFactory(factory);
    DeploymentOptions options = new DeploymentOptions();
    vertx.deployVerticle(factory.prefix() + ":" + SimpleVerticle.class.getName(), options);
    vertx.deployVerticle("org.ilmostro.vertx.verticle.SimpleReceiveVerticle", new DeploymentOptions().setInstances(4));
  }
}
