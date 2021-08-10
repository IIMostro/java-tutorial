package org.ilmostro.vertx.configuration;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("org.ilmostro.vertx")
public class VertxConfiguration {

  @Bean
  public Vertx vertx(){
      return Vertx.vertx(new VertxOptions().setWorkerPoolSize(40));
  }
}
