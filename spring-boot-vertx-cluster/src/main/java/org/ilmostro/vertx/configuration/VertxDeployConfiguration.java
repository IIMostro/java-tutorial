package org.ilmostro.vertx.configuration;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import org.ilmostro.vertx.verticle.SimpleConsumerVerticle;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VertxDeployConfiguration implements ApplicationRunner {

    private final Vertx vertx;
    private final VertxSpringFactory factory;

    public VertxDeployConfiguration(Vertx vertx, VertxSpringFactory factory) {
        this.vertx = vertx;
        this.factory = factory;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        vertx.registerVerticleFactory(factory);
        vertx.deployVerticle(factory.prefix() + ":" + SimpleConsumerVerticle.class.getName(), new DeploymentOptions().setWorker(true));
    }
}
