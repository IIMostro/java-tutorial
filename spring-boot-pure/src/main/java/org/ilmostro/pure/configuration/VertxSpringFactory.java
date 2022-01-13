package org.ilmostro.pure.configuration;

import io.vertx.core.Promise;
import io.vertx.core.Verticle;
import io.vertx.core.spi.VerticleFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;

@Component
public class VertxSpringFactory implements VerticleFactory, ApplicationContextAware {

  private ApplicationContext context;

  @Override
  public String prefix() {
    return "spring-component-";
  }

  @Override
  public void createVerticle(String verticleName, ClassLoader classLoader, Promise<Callable<Verticle>> promise) {
    String clazz = VerticleFactory.removePrefix(verticleName);
    promise.complete(() -> (Verticle) context.getBean(Class.forName(clazz)));
  }

  @Override
  public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
    this.context = applicationContext;
  }
}
