package org.ilmostro.mysql.vertx.configuration;

import io.vertx.core.Vertx;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author li.bowei
 */
@Configuration
public class VertxConfiguration {

	@Bean
	public Vertx vertx(){
		return Vertx.vertx();
	}
}
