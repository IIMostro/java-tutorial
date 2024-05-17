package org.neptune.vertx.services;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.serviceproxy.ServiceBinder;

public class SampleServiceVerticle extends AbstractVerticle {

    @Override
    public void start(Promise<Void> promise) throws Exception {
        SampleService.create("", v1 -> {
            if (v1.succeeded()){
                final var binder = new ServiceBinder(vertx);
                binder.setAddress("sample.service")
                    .register(SampleService.class, v1.result());
                promise.complete();
            }else{
                promise.fail(v1.cause());
            }
        });
    }
}
