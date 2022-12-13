package org.ilmostro.pure.configuration;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ilmostro.pure.support.RequestModelArgumentResolver;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author li.bowei
 */
@Configuration
public class RequestModelWebConfig implements WebMvcConfigurer, ApplicationContextAware{

	private ApplicationContext context;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(0, new RequestModelArgumentResolver(context.getBean(ObjectMapper.class)));
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}
}
