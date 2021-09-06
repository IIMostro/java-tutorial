package org.ilmostro.vertx.proxy.impl;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import org.ilmostro.vertx.proxy.SimpleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author li.bowei
 */
public class SimpleServiceImpl implements SimpleService {

  private static final Logger logger = LoggerFactory.getLogger(SimpleServiceImpl.class);

  public SimpleServiceImpl(Handler<AsyncResult<SimpleService>> handler) {
     handler.handle(Future.succeededFuture(this));
  }

  @Override
  public SimpleService save(String context, Handler<AsyncResult<Void>> handler) {
    handler.handle(Future.future(event -> {
      logger.info("say:{}", context);
      event.complete();
    }));
    return this;
  }
}
