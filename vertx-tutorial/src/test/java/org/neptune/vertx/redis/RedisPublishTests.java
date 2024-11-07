package org.neptune.vertx.redis;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.redis.client.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

@Slf4j
public class RedisPublishTests {

    private Vertx vertx = Vertx.vertx();

    @Slf4j
    public static class RedisPublisherVerticle extends AbstractVerticle {
        @Override
        public void start(Promise<Void> promise) throws Exception {
            final var redis = Redis.createClient(vertx, new RedisOptions().setConnectionString("redis://192.168.205.10:6379"));
            final var redisAPI = RedisAPI.api(redis);
            vertx.setPeriodic(100, id -> {
                redisAPI.publish("my-channel", String.valueOf(id), res -> {
                    if (res.succeeded()) {
                        log.info("Published message: {}", id);
                    } else {
                        log.error("Publish failed", res.cause());
                    }
                });
            });
            promise.complete();
        }
    }

    @Slf4j
    public static class RedisSubscribeVerticle extends AbstractVerticle {
        @Override
        public void start(Promise<Void> promise) throws Exception {
            final var redis = Redis.createClient(vertx, new RedisOptions()
                .setConnectionString("redis://192.168.205.10:6379"));
            redis.connect()
                .onSuccess(conn -> conn.send(Request.cmd(Command.SUBSCRIBE).arg("my-channel"), res -> {
                    if (res.succeeded()){
                        log.info("Subscribed to channel: {}", res);
                        conn.handler(v1 -> {
                           log.info("Received message: {}", v1.toString());
                        });
                    } else {
                        log.error("Subscribe failed", res.cause());
                    }
                }));
            promise.complete();
        }
    }

    @Test
    void test_publish_should_work() throws Exception {
        log.info("Deploying verticles");
        vertx.deployVerticle(RedisPublisherVerticle.class, new DeploymentOptions().setInstances(1), v2 -> {
            if (v2.succeeded()) {
                log.info("Deployed RedisPublisherVerticle: {}", v2.result());
            } else {
                log.error("Failed to deploy RedisPublisherVerticle", v2.cause());
            }
        });
        vertx.deployVerticle(RedisSubscribeVerticle.class, new DeploymentOptions().setInstances(5),
            v1 -> {
                if (v1.succeeded()) {
                    log.info("Deployed RedisSubscribeVerticle: {}", v1.result());
                    ;
                } else {
                    log.error("Failed to deploy RedisSubscribeVerticle", v1.cause());
                }
            });
        TimeUnit.SECONDS.sleep(4);
    }
}
