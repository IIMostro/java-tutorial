package org.ilmostro.webflux.configuration;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;

import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author li.bowei
 */
@Configuration
public class R2DBMySQLConfiguration {

	@Bean
	public ConnectionFactory connectionFactory(R2dbcProperties properties){
		return ConnectionFactories.get(properties.getUrl());
	}
}
