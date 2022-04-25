package org.ilmostro.mysql.vertx.configuration;

import io.vertx.core.Vertx;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author li.bowei
 */
@Configuration
@EnableConfigurationProperties(VertxMySQLProperties.class)
public class VertxConfiguration {

	@Bean
	public Vertx vertx(){
		return Vertx.vertx();
	}
}
