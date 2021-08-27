package org.ilmostro.vertx.grpc;

import io.grpc.ManagedChannel;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.grpc.VertxChannelBuilder;
import org.ilmostro.verx.grpc.HelloRequest;
import org.ilmostro.verx.grpc.VertxGreeterGrpc;

public class Client extends AbstractVerticle {

  @Override
  public void start() {
    ManagedChannel channel = VertxChannelBuilder
      .forAddress(vertx, "localhost", 8080)
      .usePlaintext()
      .build();
    VertxGreeterGrpc.GreeterVertxStub stub = VertxGreeterGrpc.newVertxStub(channel);
    HelloRequest request = HelloRequest.newBuilder().setName("Julien").build();
    stub.sayHello(request).onComplete(asyncResponse -> {
      if (asyncResponse.succeeded()) {
        System.out.println("Succeeded " + asyncResponse.result().getMessage());
      } else {
        asyncResponse.cause().printStackTrace();
      }
    });
  }

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(Client.class, new DeploymentOptions());
  }
}
