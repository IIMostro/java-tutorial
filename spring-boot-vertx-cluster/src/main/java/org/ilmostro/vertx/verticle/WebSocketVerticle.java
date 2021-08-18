package org.ilmostro.vertx.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.ServerWebSocket;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.ilmostro.vertx.configuration.VertxProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketVerticle extends AbstractVerticle {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketVerticle.class);
    private final Map<String, ServerWebSocket> instanceSocket = new ConcurrentHashMap<>();
    private final VertxProperties properties;

    public WebSocketVerticle(VertxProperties properties) {
        this.properties = properties;
    }

    @Override
    public void start(Promise<Void> promise) {
        Integer port = Optional.of("websocket.port")
                .map(System::getProperty)
                .filter(StringUtils::isNotBlank)
                .filter(NumberUtils::isDigits)
                .map(Integer::parseInt)
                .orElseGet(properties.getWebsocket()::getPort);
        vertx.createHttpServer().webSocketHandler(this::wsHandler)
                .listen(port, handler -> {
                    if (handler.succeeded()) {
                        logger.info("socket server listing in {}", port);
                        promise.complete();
                    } else {
                        promise.fail(handler.cause());
                    }
                });
        message(vertx);
        close(vertx);
    }

    private void wsHandler(ServerWebSocket socket) {
        if (socket.uri().equals("/ws")) {
            logger.info("ws accept, socket-id:{}", socket.binaryHandlerID());
            instanceSocket.put(socket.binaryHandlerID(), socket);
        } else{
            socket.reject();
        }
        socket.textMessageHandler(message -> vertx.eventBus().publish("ws.socket.message", message))
                .exceptionHandler(ex -> logger.error("send message error! cause:{}", ex.getMessage()));
        socket.closeHandler(handler -> vertx.eventBus().publish("ws.socket.close", socket.binaryHandlerID()));
    }

    private void message(Vertx vertx){
        vertx.eventBus().consumer("ws.socket.message", handler -> instanceSocket.values().forEach(var1 -> var1.writeTextMessage(handler.body().toString())));
    }

    private void close(Vertx vertx){
        vertx.eventBus().consumer("ws.socket.close", handler -> instanceSocket.remove(handler.body().toString()));
    }
}
