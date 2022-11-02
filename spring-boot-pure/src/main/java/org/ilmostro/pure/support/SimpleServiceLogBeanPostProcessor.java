package org.ilmostro.pure.support;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.ilmostro.pure.service.SimpleService;
import org.ilmostro.pure.service.impl.SimpleServiceImpl;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

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
	public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

	}

	private Class<?> logger(Class<?> target){
		return new ByteBuddy()
				.subclass(target)
				.method(ElementMatchers.not(ElementMatchers.isDeclaredBy(Object.class)))
				.intercept(MethodDelegation.to(LoggerServiceWrapper.class))
				.make()
				.load(classLoader, ClassLoadingStrategy.Default.INJECTION)
				.getLoaded();
	}

	@Override
	public void postProcessBeanDefinitionRegistry(@NonNull BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
		final Class<?> logger = logger(SimpleServiceImpl.class);
//		final Class<?> aClass = ClassUtils.resolveClassName(logger.getName(), beanClassLoader);
		final AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(logger)
				.setPrimary(true)
				.getBeanDefinition();
		BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinition, beanDefinitionRegistry);
	}
}
