package org.ilmostro.pure.verticle;

import io.vertx.core.AbstractVerticle;
import org.ilmostro.pure.service.SimpleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SimpleConsumerVerticle extends AbstractVerticle {

    private static final Logger logger = LoggerFactory.getLogger(SimpleConsumerVerticle.class);
    private final SimpleService service;

    public SimpleConsumerVerticle(SimpleService service) {
        this.service = service;
    }

    @Override
    public void start() throws Exception {
        logger.info("simple consumer verticle start");
        vertx.eventBus().consumer("ilmostro.handler",
                handler -> {
                    logger.info("receive message address:{}, body:{}", handler.address(), handler.body());
                    service.say(handler.body().toString());
                });
    }
}
