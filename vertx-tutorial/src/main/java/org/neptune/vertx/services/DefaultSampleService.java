package org.neptune.vertx.services;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import org.neptune.vertx.cluster.HazelcastClusterVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DefaultSampleService implements SampleService {

    private static final Logger log = LoggerFactory.getLogger(HazelcastClusterVerticle.class);
    private final String prefix;

    DefaultSampleService(String prefix, Handler<AsyncResult<SampleService>> handler) {
        this.prefix = prefix;
        handler.handle(Future.succeededFuture(this));
    }

    @Override
    public SampleService hello(String value, Handler<AsyncResult<JsonObject>> handler) {
        log.info("Hello {}", value);
        final var json = new JsonObject();
        json.put("key", prefix + "Hello " + value);
        handler.handle(Future.succeededFuture(json));
        return this;
    }
}
