package org.ilmostro.pure.support;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author li.bowei
 */

public class MeterRegistrySupport extends WebMvcConfigurationSupport implements HandlerInterceptor {

	private final MeterRegistry registry;
	private final Map<String, Counter> counters = new ConcurrentHashMap<>();
	private final Map<String, Timer> timers = new ConcurrentHashMap<>();

	private static final String METER_TIME_ATTRIBUTE_KEY = "meter.time.start";

	protected MeterRegistrySupport(MeterRegistry registry){
		this.registry = registry;
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			@NonNull HttpServletResponse response,
			@NonNull Object handler) {
		request.setAttribute(METER_TIME_ATTRIBUTE_KEY, System.currentTimeMillis());
		return true;
	}

	@Override
	protected void addInterceptors(@NonNull InterceptorRegistry registry) {
		registry.addInterceptor(this);
	}

	@Override
	public void postHandle(HttpServletRequest request,@NonNull HttpServletResponse response,
			@NonNull Object handler, ModelAndView modelAndView) {
		String uri = request.getRequestURI();
		Counter counter = counters.computeIfAbsent(uri, v1 -> registry.counter("application.http.request.counter", uri, String.valueOf(response.getStatus())));
		counter.increment();
	}
}
