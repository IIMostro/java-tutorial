package org.neptune.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.client.WebClient;
import org.neptune.vertx.services.SampleService;
import org.neptune.vertx.services.SampleServiceVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerVerticle extends AbstractVerticle {

    private static final Logger log = LoggerFactory.getLogger(ServerVerticle.class);

    @Override
    public void start(Promise<Void> start) throws Exception {
        log.info("vertx is clustered: {}", vertx.isClustered());
        final var server = vertx.createHttpServer();
        final var router = Router.router(vertx);
        final var subRouter = Router.router(vertx);
        subRouter.route().handler(v1 -> {
            final var client = WebClient.create(vertx);
            client.get("www.baidu.com", "/")
                .send()
                .onSuccess(response -> {
                    log.info("Got response: {}", response.bodyAsString());
                    v1.response().end(response.bodyAsString());
                }).onFailure(err -> {
                    log.error("Got error", err);
                    v1.response().end("Error");
                });
        });
        Promise<String> dbVerticleDeployment = Promise.promise();
        vertx.deployVerticle(new SampleServiceVerticle(), dbVerticleDeployment);
        final var proxy = SampleService.createProxy(vertx, "sample.service");
        log.info("proxy: {}", proxy);
        router.get("/services").handler(v1 -> {
            proxy.hello("world", v2 -> {
                if (v2.succeeded()){
                    log.info("Got response: {}", v2.result());
                    v1.response().end(v2.result().encode());
                } else{
                    log.error("Got error", v2.cause());
                    v1.response().end(v2.cause().toString());
                }
            });
        });
        router.mountSubRouter("/baidu", subRouter);
        final var infoq = Router.router(vertx);
        router.mountSubRouter("/infoq", infoq);
        infoq.route().handler(ctx -> {
            final var request = ctx.request();
            log.info("Got request: {} {}", request.method(), request.uri());
            final var response = ctx.response();
            response.end("Hello World from InfoQ");
        });
        router.route().handler(v1 -> v1.response().end());
        server.requestHandler(router).listen(8080);
        start.complete();
    }
}
