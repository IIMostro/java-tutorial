package org.ilmostro.pure.configuration;

import java.util.Collections;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import org.ilmostro.pure.utils.ThreadPoolExecutorFactory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;

/**
 * @author li.bowei
 */
@Configuration
public class ThreadPoolExecutorMeter implements ApplicationContextAware, InitializingBean {

	private static final Iterable<Tag> TAG = Collections.singletonList(Tag.of("thread.pool.name", "BasicThreadPool"));
	private ApplicationContext context;

	@Override
	public void afterPropertiesSet() {
		Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(new MeterRunnable(ThreadPoolExecutorFactory.get(),
				context.getBean(MeterRegistry.class)), 0, 5, TimeUnit.SECONDS);
	}

	@Override
	public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}


	private static class MeterRunnable implements Runnable{

		private final ThreadPoolExecutor executor;
		private final MeterRegistry registry;
		private MeterRunnable(ThreadPoolExecutor executor, MeterRegistry registry) {
			this.executor = executor;
			this.registry = registry;
		}

		@Override
		public void run() {
			try {
				registry.gauge("thread.pool.core.size", TAG, executor, ThreadPoolExecutor::getCorePoolSize);
				registry.gauge("thread.pool.largest.size", TAG, executor, ThreadPoolExecutor::getLargestPoolSize);
				registry.gauge("thread.pool.max.size", TAG, executor, ThreadPoolExecutor::getMaximumPoolSize);
				registry.gauge("thread.pool.active.size", TAG, executor, ThreadPoolExecutor::getActiveCount);
				registry.gauge("thread.pool.thread.count", TAG, executor, ThreadPoolExecutor::getPoolSize);
				registry.gauge("thread.pool.queue.size", TAG, executor, e -> e.getQueue().size());
				registry.gauge("thread.pool.queue.capacity", TAG, executor, e -> e.getQueue().remainingCapacity());
			}
			catch (Exception e) {
				//ignore
			}
		}
	}
}
