package org.ilmostro.pure.vertx;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import org.junit.Before;
import org.junit.Test;

/**
 * @author li.bowei
 */
public class WebSocketTests {

    Vertx vertx;
    @Before
    public void before(){
        vertx = Vertx.vertx();
    }

    @Test
    public void start() {
        HttpServer server = vertx.createHttpServer().webSocketHandler(v1 -> {
            if (v1.path().equals("/socket")) {
                v1.textMessageHandler(v2 -> {
                    System.out.println("receive message: " + v2);
                    v1.writeTextMessage(String.valueOf(System.currentTimeMillis()));
                });
            }
        });
        server.listen(8081, v1 -> System.out.println("websocket start in 8081..."));
    }
}
