package org.ilmostro.pure.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.ilmostro.pure.annotation.Logger;
import org.ilmostro.pure.annotation.LoggerSupport;
import org.ilmostro.pure.service.SimpleService;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
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

	private static final Collection<EnhanceSupport> ENHANCE_CLASS_SUPPORT = new ArrayList<>();

	static {
		ENHANCE_CLASS_SUPPORT.add(new MethodEnhanceClassSupport());
		ENHANCE_CLASS_SUPPORT.add(new TypeEnhanceClassSupport());
	}

	@Override
	public Object postProcessAfterInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
		if (!(bean instanceof SimpleService)) return bean;
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(SimpleService.class);
		final Object target = bean;
		// 1. object cglib生成的对象,也就是enhancer.create()返回的对象, 如果在里面使用的话则会有爆栈的危险
		// 2. method 方法
		// 3. objects 方法参数
		// 4. methodProxy 方法代理
		enhancer.setCallback((MethodInterceptor) (object, method, objects, proxy) -> {
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

			final LoggerSupport annotation = AnnotationUtils.findAnnotation(clazz, LoggerSupport.class);
			if (Objects.isNull(annotation)){
				continue;
			}

			Class<?> enhance = null;
			for (EnhanceSupport support : ENHANCE_CLASS_SUPPORT) {
				enhance = support.enhance(clazz, classLoader, Logger.class, LoggerServiceWrapper.class);
				if (Objects.nonNull(enhance)) {
					break;
				}
			}

			if (Objects.isNull(enhance)) {
				throw new RuntimeException("can't find any support enhance");
			}

			// 获取原始的beanDefinition
			final BeanDefinition originalBeanDefinition = beanDefinitionRegistry.getBeanDefinition(beanDefinitionName);
			// 新建一个
			final AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(enhance)
					.getBeanDefinition();
			// 从original覆盖过来
			beanDefinition.overrideFrom(originalBeanDefinition);
			log.debug("BeanDefinition:[{}] use logger wrapper, unload original definition, register new BeanDefinition:[{}]",
					originalBeanDefinition, beanDefinition);
			beanDefinitionRegistry.removeBeanDefinition(beanDefinitionName);
			BeanDefinitionReaderUtils.registerBeanDefinition(new BeanDefinitionHolder(beanDefinition, beanDefinitionName), beanDefinitionRegistry);
		}
	}
}
