package org.ilmostro.pure.utils;

import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cn.hutool.core.thread.ExecutorBuilder;
import cn.hutool.core.thread.ThreadFactoryBuilder;

/**
 * @author li.bowei
 */
public class ThreadPoolExecutorFactory {

	private static final int CORE_SIZE = Integer.SIZE;
	private static volatile ThreadPoolExecutor executor;

	public static ThreadPoolExecutor get() {
		return get(false);
	}

	public static ThreadPoolExecutor get(boolean daemon) {
		return get(CORE_SIZE, daemon, false);
	}

	/**
	 * 创建ThreadPoolExecutor, 如果是CPU密集的话则不需要队列，任务来了尽快处理。并且将MaxPoolSize设置为最大
	 *
	 * @param core 核心线程
	 * @param daemon 是否守护线程
	 * @param dense 是否CPU密集
	 * @return 线程池
	 */
	public static ThreadPoolExecutor get(int core, boolean daemon, boolean dense) {
		if (Objects.nonNull(executor)) return executor;
		synchronized (ThreadPoolExecutorFactory.class) {
			if (Objects.nonNull(executor)) return executor;
			executor = ExecutorBuilder.create()
					.setCorePoolSize(core)
					.setMaxPoolSize(dense ? Integer.MAX_VALUE : CORE_SIZE << 1)
					.setKeepAliveTime(1, TimeUnit.MINUTES)
					.setWorkQueue(dense ? new SynchronousQueue<>() : new LinkedBlockingQueue<>(10000))
					.setThreadFactory(ThreadFactoryBuilder.create().setDaemon(daemon)
							.setNamePrefix("customize-").build())
					.setHandler((r, executor) -> {
					})
					.build();
			return executor;
		}
	}
}
