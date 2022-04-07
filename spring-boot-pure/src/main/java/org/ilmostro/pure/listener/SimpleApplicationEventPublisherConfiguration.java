package org.ilmostro.pure.listener;

import java.util.concurrent.Executors;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.lang.NonNull;

/**
 * @author li.bowei
 */
//@Component
public class SimpleApplicationEventPublisherConfiguration implements ApplicationContextAware, InitializingBean {

	private ApplicationContext context;

	@Override
	public void afterPropertiesSet() {
		SimpleApplicationEventMulticaster multicaster = (SimpleApplicationEventMulticaster)
				context.getBean(AbstractApplicationContext.APPLICATION_EVENT_MULTICASTER_BEAN_NAME);
		multicaster.setTaskExecutor(Executors.newFixedThreadPool(5));
	}

	@Override
	public void setApplicationContext(@NonNull ApplicationContext context) throws BeansException {
		this.context = context;
	}
}
