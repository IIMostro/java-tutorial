package org.neptune.vertx;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.client.WebClient;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Server {

//    private static final Vertx vertx = Vertx.vertx();

//    public static void main(String[] args) {
//        final var server = vertx.createHttpServer();
//        final var router = Router.router(vertx);
//        router.route().handler(ctx -> {
//            final var request = ctx.request();
//            log.info("Got request: {} {}", request.method(), request.uri());
//            final var client = WebClient.create(vertx);
//            client.get("www.baidu.com", "/")
//                .send()
//                .onSuccess(response -> {
//                    log.info("Got response: {}", response.bodyAsString());
//                    ctx.response().end(response.bodyAsString());
//                }).onFailure(err -> {
//                    log.error("Got error", err);
//                    ctx.response().end("Error");
//                });
//        });
//        server.requestHandler(router).listen(8080);
//    }
}
