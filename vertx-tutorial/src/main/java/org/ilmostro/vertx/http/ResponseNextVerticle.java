package org.ilmostro.vertx.http;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResponseNextVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> promise) throws Exception {

    Router router = Router.router(vertx);
    Route route = router.route("/hello");
    route.handler(ctx -> {
      HttpServerResponse response = ctx.response();
      response.setChunked(true);
      response.write("this is first chunk");
      ctx.vertx().setTimer(1000, tid -> ctx.next());
    });
    route.handler(ctx ->{
      HttpServerResponse response = ctx.response();
      response.write("this is second chunk");
      ctx.vertx().setTimer(1000, tid -> ctx.next());
    });
    route.handler(ctx ->{
      HttpServerResponse response = ctx.response();
      response.write("this is third chunk");
      ctx.response().end();
    });
    vertx.createHttpServer().requestHandler(router)
      .listen(8080, handler ->{
        if(handler.succeeded()){
//          log.info("http server start in 8080...");
          promise.complete();
        }else{
          promise.fail(handler.cause());
        }
      });
  }

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(ResponseNextVerticle.class, new DeploymentOptions());
  }
}
