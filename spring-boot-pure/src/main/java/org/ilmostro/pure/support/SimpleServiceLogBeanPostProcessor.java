package org.ilmostro.pure.support;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.NamedElement;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.ilmostro.pure.annotation.LoggerWrapper;
import org.ilmostro.pure.service.SimpleService;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

/**
 * @author li.bowei
 */
@Service
@Slf4j
public class SimpleServiceLogBeanPostProcessor implements BeanPostProcessor, BeanDefinitionRegistryPostProcessor, BeanClassLoaderAware {

	private ClassLoader classLoader;

	@Override
	public Object postProcessAfterInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
		if (!(bean instanceof SimpleService)) return bean;
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(SimpleService.class);
		final Object target = bean;
		enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> {
			final long start = System.currentTimeMillis();
			final Object invoke = method.invoke(target, objects);
			log.info("方法执行消耗了:[{}]", System.currentTimeMillis() - start);
			return invoke;
		});
		return enhancer.create();
	}

	@Override
	public void setBeanClassLoader(@NonNull ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	@Override
	public void postProcessBeanFactory(@NonNull ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

	}

	private Class<?> type(Class<?> target) {
		return new ByteBuddy()
				.subclass(target)
//				.method(ElementMatchers.isAnnotatedWith(LoggerWrapper.class))
				.method(ElementMatchers.not(ElementMatchers.isDeclaredBy(Object.class)))
				.intercept(MethodDelegation.to(LoggerServiceWrapper.class))
				.make()
				.load(classLoader, ClassLoadingStrategy.Default.INJECTION)
				.getLoaded();
	}

	private Class<?> method(Class<?> target) {
		return new ByteBuddy()
				.subclass(target)
				.method(ElementMatchers.isAnnotatedWith(LoggerWrapper.class))
//				.method(ElementMatchers.not(ElementMatchers.isDeclaredBy(Object.class)))
				.intercept(MethodDelegation.to(LoggerServiceWrapper.class))
				.make()
				.load(classLoader, ClassLoadingStrategy.Default.INJECTION)
				.getLoaded();
	}


	@Override
	public void postProcessBeanDefinitionRegistry(@NonNull BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
		for (String beanDefinitionName : beanDefinitionRegistry.getBeanDefinitionNames()) {
			final Class<?> clazz = Optional.of(beanDefinitionName)
					.map(beanDefinitionRegistry::getBeanDefinition)
					.map(BeanDefinition::getBeanClassName)
					.map(v1 -> ClassUtils.resolveClassName(v1, classLoader))
					.orElse(null);
			if (Objects.isNull(clazz)) {
				continue;
			}
			final List<Method> methodsListWithAnnotation = MethodUtils.getMethodsListWithAnnotation(clazz, LoggerWrapper.class);
			if (Objects.nonNull(AnnotationUtils.findAnnotation(clazz, LoggerWrapper.class))) {
				beanDefinitionRegistry.removeBeanDefinition(beanDefinitionName);
				final AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(type(clazz))
						.getBeanDefinition();
				BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinition, beanDefinitionRegistry);
			}

			if (CollectionUtils.isNotEmpty(methodsListWithAnnotation)) {
				beanDefinitionRegistry.removeBeanDefinition(beanDefinitionName);
				final AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(method(clazz))
						.getBeanDefinition();
				BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinition, beanDefinitionRegistry);
			}
		}
	}
}
