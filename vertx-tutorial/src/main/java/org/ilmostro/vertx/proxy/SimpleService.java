package org.ilmostro.vertx.proxy;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import org.ilmostro.vertx.proxy.impl.SimpleServiceImpl;

/**
 * @author li.bowei
 */
@ProxyGen
@VertxGen
public interface SimpleService {

  @Fluent
  SimpleService save(String context, Handler<AsyncResult<Void>> handler);

  @GenIgnore
  static SimpleService create(Handler<AsyncResult<SimpleService>> handler){
    return new SimpleServiceImpl(handler);
  }

  @GenIgnore
  static SimpleService createProxy(Vertx vertx, String address){
    return new SimpleServiceVertxEBProxy(vertx, address);
  }
}
