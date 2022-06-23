package org.ilmostro.pure.configuration;

import java.util.List;

import org.ilmostro.pure.support.SignatureHandlerMethodArgumentResolver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.lang.NonNull;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author li.bowei
 */
@Configuration
public class SignatureWebConfigure implements WebMvcConfigurer {

	private final List<HttpMessageConverter<?>> converters;

	public SignatureWebConfigure(List<HttpMessageConverter<?>> converters) {
		this.converters = converters;
	}

	@Bean
	public HandlerMethodArgumentResolver signature(){
		return new SignatureHandlerMethodArgumentResolver(converters);
	}

	@Override
	public void addArgumentResolvers(@NonNull List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(signature());
	}
}
