package org.ilmostro.pure.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;

public class EventBusTests {

    @Test
    void test_event_bus_publish_should_work() throws Exception {
        final Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new SimpleVerticle());
        final EventBus eventBus = vertx.eventBus();
        // 消息进入的拦截器
        eventBus.addInboundInterceptor(handler -> {
            MDC.put("traceId", handler.message().headers().get("traceId"));
            System.out.println("inbound: " + handler.body() + "current thread name" + Thread.currentThread().getName());
            handler.next();
        });
        // 发出去消息的拦截器
//        eventBus.addOutboundInterceptor(handler -> {
//            System.out.println("outbound: " + handler.body() + "current thread name" + Thread.currentThread().getName());
//            handler.next();
//        });
        CountDownLatch latch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            final DeliveryOptions options = new DeliveryOptions();
            final String traceId = UUID.randomUUID().toString();
            options.addHeader("traceId", traceId);
            System.out.println("send message: " + i + "-current thread name-" + Thread.currentThread().getName() + "-mdc-" + traceId);
            eventBus.request("test", i, options, handler -> latch.countDown());
        }
        latch.await();
    }


    static class SimpleVerticle extends AbstractVerticle {

        @Override
        public void start(Promise<Void> promise) {
            vertx.eventBus().consumer("test", handler -> {
                System.out.println("receive" + handler.body() + " current thread name" + Thread.currentThread().getName() + "-mdc-" + MDC.get("traceId"));
                System.out.println(MDC.getCopyOfContextMap().size());
            });
            promise.complete();
        }
    }
}
