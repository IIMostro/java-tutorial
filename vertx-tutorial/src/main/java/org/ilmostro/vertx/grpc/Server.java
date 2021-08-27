package org.ilmostro.vertx.grpc;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.grpc.VertxServerBuilder;
import org.ilmostro.verx.grpc.HelloReply;
import org.ilmostro.verx.grpc.HelloRequest;
import org.ilmostro.verx.grpc.VertxGreeterGrpc;

public class Server extends AbstractVerticle {

  @Override
  public void start() throws Exception {
    VertxServerBuilder.forAddress(vertx, "localhost", 8080).addService(new VertxGreeterGrpc.GreeterVertxImplBase() {
      @Override
      public Future<HelloReply> sayHello(HelloRequest request) {
        System.out.println("Hello " + request.getName());
        return Future.succeededFuture(HelloReply.newBuilder().setMessage(request.getName()).build());
      }
    }).build()
      .start(ar -> {
        if (ar.succeeded()) {
          System.out.println("gRPC service started");
        } else {
          System.out.println("Could not start server " + ar.cause().getMessage());
        }
      });
  }

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(Server.class, new DeploymentOptions().setInstances(4));
  }
}
