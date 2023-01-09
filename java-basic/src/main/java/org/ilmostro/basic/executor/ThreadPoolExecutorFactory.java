package org.ilmostro.basic.executor;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cn.hutool.core.thread.ExecutorBuilder;
import cn.hutool.core.thread.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

/**
 * @author li.bowei
 * @date 2020/7/23 16:40
 */
@Slf4j
public class ThreadPoolExecutorFactory {

	private static ThreadPoolExecutor executor;

	public static ThreadPoolExecutor get(String prefix) {
		return get(5, false, prefix);
	}


	public static ThreadPoolExecutor get(boolean daemon) {
		return get(5, daemon, "customize-");
	}

	public static ThreadPoolExecutor get(int core, boolean daemon, String prefix) {
		if (Objects.isNull(executor)) {
			synchronized (ThreadPoolExecutorFactory.class){
				executor = ExecutorBuilder.create()
						.setCorePoolSize(core)
						.setMaxPoolSize(10)
						.setKeepAliveTime(30, TimeUnit.SECONDS)
						.setWorkQueue(new LinkedBlockingQueue<>(1000))
						.setThreadFactory(ThreadFactoryBuilder.create().setDaemon(daemon)
								.setNamePrefix(prefix).build())
						.setHandler((r, handler) -> {

						})
						.build();
			}
		}else{
			if (executor.getCorePoolSize() == core){
				return executor;
			}
			synchronized (ThreadPoolExecutorFactory.class){
				executor.setCorePoolSize(core);
			}
		}
		return executor;
	}

	public static Runnable wrap(Runnable runnable){
		final Map<String, String> context = MDC.getCopyOfContextMap();
		return () -> {
			try {
				MDC.setContextMap(context);
				runnable.run();
				MDC.clear();
			} catch (Exception e) {
				log.error("runnable execute error", e);
			}
		};
	}
}
