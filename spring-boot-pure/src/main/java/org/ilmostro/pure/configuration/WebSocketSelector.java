package org.ilmostro.pure.configuration;

import java.util.Map;
import java.util.Objects;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import org.ilmostro.pure.annotation.EnableWebSocket;
import org.ilmostro.pure.processor.VerticleBeanPostProcessor;
import org.ilmostro.pure.socket.CustomChannelInboundHandlerAdapter;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.CollectionUtils;

/**
 * @author li.bowei
 */
public class WebSocketSelector implements ImportSelector, BeanPostProcessor, ApplicationContextAware {

	private ApplicationContext context;

	@Override
	public String[] selectImports(AnnotationMetadata metadata) {
		Map<String, Object> attributes = metadata
				.getAnnotationAttributes(EnableWebSocket.class.getName());
		if (CollectionUtils.isEmpty(attributes) || Objects.isNull(attributes.get("value"))) return new String[]{};
		Object value = attributes.get("value");
		if (EnableWebSocket.WebSocketType.vertx.equals(value)) {
			return new String[]{VertxConfiguration.class.getName(), VertxSpringFactory.class.getName(), VerticleBeanPostProcessor.class.getName()};
		}else{
			return new String[]{CustomChannelInboundHandlerAdapter.class.getName(), NettyBootstrapRunner.class.getName()};
		}
	}

//	@Override
//	public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry, BeanNameGenerator generator) {
//		Map<String, Object> attributes = metadata
//				.getAnnotationAttributes(EnableWebSocket.class.getName());
//		if (CollectionUtils.isEmpty(attributes) || Objects.isNull(attributes.get("value"))) return;
//		Object value = attributes.get("value");
//		RootBeanDefinition definition;
//		if (EnableWebSocket.WebSocketType.vertx.equals(value)){
//			RootBeanDefinition configuration = new RootBeanDefinition(VertxConfiguration.class);
//			registry.registerBeanDefinition(generator.generateBeanName(configuration, registry), configuration);
//			definition = new RootBeanDefinition(WebSocketVerticle.class);
//		}else{
//			RootBeanDefinition adapter = new RootBeanDefinition(CustomChannelInboundHandlerAdapter.class);
//			registry.registerBeanDefinition(generator.generateBeanName(adapter, registry), adapter);
//			definition = new RootBeanDefinition(NettyBootstrapRunner.class);
//		}
//		registry.registerBeanDefinition(generator.generateBeanName(definition, registry), definition);
//	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}
}
