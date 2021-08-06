package org.ilmostro.vertx;

import io.vertx.core.*;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

import java.util.concurrent.TimeUnit;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {

    Vertx.vertx(new VertxOptions().setWorkerPoolSize(40));
    // Create a Router
    Router router = Router.router(vertx);

    // Mount the handler for all incoming requests at every path and HTTP method
    router.route("/hello").blockingHandler(handler -> {
      try {
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      handler.json("hello world!");
    });
//    router.route().handler(context -> {
//      // Get the address of the request
//      String address = context.request().connection().remoteAddress().toString();
//      // Get the query parameter "name"
//      MultiMap queryParams = context.queryParams();
//      String name = queryParams.contains("name") ? queryParams.get("name") : "unknown";
//      // Write a json response
//      context.json(
//        new JsonObject()
//          .put("name", name)
//          .put("address", address)
//          .put("message", "Hello " + name + " connected from " + address)
//      );
//    });

    vertx.createHttpServer().requestHandler(router)
      .listen(8888, http -> {
        if (http.succeeded()) {
          startPromise.complete();
          System.out.println("HTTP server started on port 8888");
        } else {
          startPromise.fail(http.cause());
        }
      });

    vertx.createHttpServer().webSocketHandler(handler -> {
      handler.writeTextMessage("1");
    }).listen(8889);
  }
}
