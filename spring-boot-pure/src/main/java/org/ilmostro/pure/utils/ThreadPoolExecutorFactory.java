package org.ilmostro.pure.utils;

import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cn.hutool.core.thread.ExecutorBuilder;
import cn.hutool.core.thread.ThreadFactoryBuilder;

/**
 * @author li.bowei
 */
public class ThreadPoolExecutorFactory {

	private static volatile ThreadPoolExecutor executor;

	public static ThreadPoolExecutor get() {
		return get(false);
	}

	public static ThreadPoolExecutor get(boolean daemon) {
		return get(10, daemon);
	}

	public static ThreadPoolExecutor get(int core, boolean daemon) {
		if (Objects.nonNull(executor)) return executor;
		synchronized (ThreadPoolExecutorFactory.class) {
			if (Objects.nonNull(executor)) return executor;
			executor = ExecutorBuilder.create()
					.setCorePoolSize(core)
					.setMaxPoolSize(10)
					.setKeepAliveTime(30, TimeUnit.SECONDS)
//					.setWorkQueue(new LinkedBlockingQueue<>(10000))
					.setThreadFactory(ThreadFactoryBuilder.create().setDaemon(daemon)
							.setNamePrefix("customize-").build())
					.setHandler((r, executor) -> {
					})
					.build();
			return executor;
		}
	}
}
