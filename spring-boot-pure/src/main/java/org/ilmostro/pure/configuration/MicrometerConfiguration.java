package org.ilmostro.pure.configuration;

import io.micrometer.core.aop.CountedAspect;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;

import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author li.bowei
 */
@Configuration
public class MicrometerConfiguration {

	final ConfigurableEnvironment environment;

	public MicrometerConfiguration(ConfigurableEnvironment environment) {
		this.environment = environment;
	}

	@Bean
	public MeterRegistryCustomizer<MeterRegistry> configurer() {
		return (registry) -> registry.config().commonTags("application", environment.getProperty("spring.application.name"));
	}

	@Bean
	public TimedAspect timedAspect(MeterRegistry registry){
		return new TimedAspect(registry);
	}

	@Bean
	public CountedAspect countedAspect(MeterRegistry registry){
		return new CountedAspect(registry);
	}

}
