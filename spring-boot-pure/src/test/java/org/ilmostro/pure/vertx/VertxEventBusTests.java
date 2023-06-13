package org.ilmostro.pure.vertx;

import io.vertx.core.Vertx;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class VertxEventBusTests {

    @Test
    void test_event_bus_request() throws Exception {
        final var vertx = Vertx.vertx();
        vertx.eventBus().consumer("test", v1 -> {
            System.out.println("receive 1 message: " + Thread.currentThread().getName() + v1.body());
            v1.reply("world1");
        });

        vertx.eventBus().consumer("test", v1 -> {
            System.out.println("receive 2 message: " + Thread.currentThread().getName() + v1.body());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            v1.reply("world2");
        });

        for (int i = 0; i < 10; i++) {
            vertx.eventBus().publisher("test").write("hello" + i).onComplete(v1 -> {
                System.out.println("send message: " + Thread.currentThread().getName() + v1.result());
            });
        }

        TimeUnit.SECONDS.sleep(3);
    }
}
