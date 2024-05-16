package org.neptune.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.client.WebClient;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServerVerticle extends AbstractVerticle {

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
        router.mountSubRouter("/baidu", subRouter);
        router.route().handler(ctx -> {
            final var request = ctx.request();
            log.info("Got request: {} {}", request.method(), request.uri());
            ctx.next();
        });
        final var infoq = Router.router(vertx);
        router.mountSubRouter("/infoq", infoq);
        infoq.route().handler(ctx -> {
            final var request = ctx.request();
            log.info("Got request: {} {}", request.method(), request.uri());
            final var response = ctx.response();
            response.end("Hello World from InfoQ");
        });
//        router.mountSubRouter("/infoq", )
        router.route().handler(v1 -> v1.response().end());
        server.requestHandler(router).listen(8080);
        start.complete();
    }
}
