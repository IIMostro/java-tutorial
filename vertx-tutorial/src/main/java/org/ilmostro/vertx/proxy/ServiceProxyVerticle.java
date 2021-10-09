package org.ilmostro.vertx.proxy;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.serviceproxy.ServiceBinder;

/**
 * @author li.bowei
 */
public class ServiceProxyVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> promise) throws Exception {
    SimpleService.create(handler -> {
      if(handler.succeeded()){
        ServiceBinder binder = new ServiceBinder(vertx);
        binder.setAddress("org.ilmostro.vertx.proxy.SimpleService");
        binder.register(SimpleService.class, handler.result());
        promise.complete();
      }else{
        promise.fail(handler.cause());
      }
    });
  }

  @Override
  public void start() throws Exception {
    SimpleService service = SimpleService.createProxy(vertx, "org.ilmostro.vertx.proxy.SimpleService");
    service.save("context", handler ->{
      if (handler.succeeded()) {

      }
    });

  }
}
