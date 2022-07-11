package org.ilmostro.pure.support;

import lombok.extern.slf4j.Slf4j;
import org.ilmostro.pure.service.SimpleService;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

/**
 * @author li.bowei
 */
@Service
@Slf4j
public class SimpleServiceLogBeanPostProcessor implements BeanPostProcessor {

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
}
