package org.neptune.vertx.services;


import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

@ProxyGen
@VertxGen
public interface SampleService {

    @Fluent
    SampleService hello(String value, Handler<AsyncResult<JsonObject>> handler);

    @GenIgnore
    static SampleService create(String prefix, Handler<AsyncResult<SampleService>> handler){
        return new DefaultSampleService(prefix, handler);
    }

    @GenIgnore
    static SampleService createProxy(Vertx vertx, String address){
        return new SampleServiceVertxEBProxy(vertx, address);
    }
}
