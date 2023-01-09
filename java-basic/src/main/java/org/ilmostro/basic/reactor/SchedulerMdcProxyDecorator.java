package org.ilmostro.basic.reactor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import org.slf4j.MDC;
import reactor.core.scheduler.Scheduler;

/**
 * @author li.bowei
 */
public class SchedulerMdcProxyDecorator implements BiFunction<Scheduler, ScheduledExecutorService, ScheduledExecutorService> {

	@Override
	public ScheduledExecutorService apply(Scheduler scheduler, ScheduledExecutorService scheduledExecutorService) {
		return (ScheduledExecutorService) Proxy.newProxyInstance(SchedulerMdcProxyDecorator.class.getClassLoader(),
				new Class[]{ScheduledExecutorService.class},
				new MdcDecoratingInvocationHandler(scheduledExecutorService));
	}

	static final class MdcDecoratingInvocationHandler implements InvocationHandler {
		private final ScheduledExecutorService delegate;

		public MdcDecoratingInvocationHandler(ScheduledExecutorService delegate) {
			this.delegate = delegate;
		}

		@Override
		@SuppressWarnings("unchecked")
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			Class<?>[] paramTypes = method.getParameterTypes();
			if (paramTypes.length == 0) {
				return method.invoke(this.delegate, args); // no replace, simply proceed
			}

			Class<?> firstParamType = paramTypes[0];

			// swap Runnable/Callable/Collection<? extends Callable<?>)
			Object swapped;
			if (firstParamType.isAssignableFrom(Runnable.class)) {
				swapped = wrap((Runnable) args[0]);
			} else if (firstParamType.isAssignableFrom(Callable.class)) {
				swapped = wrap((Callable<?>) args[0]);
			} else if (firstParamType.isAssignableFrom(Collection.class)) { // see the ExecutorService API
				swapped = ((Collection<? extends Callable<?>>) args[0]).stream()
						.map(this::wrap)
						.collect(Collectors.toList());
			} else {
				return method.invoke(this.delegate, args); // bail out, no replace needed
			}
			args[0] = swapped;  // swap

			return method.invoke(this.delegate, args);
		}


		private Runnable wrap(Runnable runnable) {
			Map<String, String> map = MDC.getCopyOfContextMap();
			return () -> {
				if (map != null) {
					MDC.setContextMap(map);
				}
				try {
					runnable.run();
				} finally {
					MDC.clear();
				}
			};
		}

		private Callable<?> wrap(Callable<?> callable) {
			Map<String, String> map = MDC.getCopyOfContextMap();
			return () -> {
				if (map != null) {
					MDC.setContextMap(map);
				}
				try {
					return callable.call();
				} finally {
					MDC.clear();
				}
			};
		}
	}
}
