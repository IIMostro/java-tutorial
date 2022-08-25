package org.ilmostro.pure.configuration;

import java.util.List;

import org.ilmostro.pure.support.RequestModelArgumentResolver;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author li.bowei
 */
@Configuration
public class RequestModelWebConfig implements WebMvcConfigurer {

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(0, new RequestModelArgumentResolver());
	}
}
