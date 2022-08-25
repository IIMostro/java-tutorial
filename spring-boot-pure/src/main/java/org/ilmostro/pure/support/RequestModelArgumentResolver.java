package org.ilmostro.pure.support;

import java.io.BufferedReader;
import java.lang.reflect.Type;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.ilmostro.pure.annotation.RequestModel;

import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author li.bowei
 */
@Slf4j
public class RequestModelArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(@NonNull MethodParameter parameter) {
		return parameter.hasParameterAnnotation(RequestModel.class);
	}

	@Override
	public Object resolveArgument(@NonNull MethodParameter parameter,
			ModelAndViewContainer container,
			@NonNull NativeWebRequest request,
			WebDataBinderFactory factory) throws Exception {
		final HttpServletRequest httpServletRequest = ((ServletWebRequest) request).getRequest();
		final String method = httpServletRequest.getMethod();
		final String contentType = httpServletRequest.getContentType();
		log.info("method:{}, content-type:{}", method, contentType);
		final Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();

		final Type type = parameter.getGenericParameterType();
		final BufferedReader reader = httpServletRequest.getReader();
		final String s = reader.lines().reduce(String::concat).orElse("");
		return JSON.parseObject(s, type);
	}
}
