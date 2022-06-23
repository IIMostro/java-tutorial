package org.ilmostro.pure.support;

import java.util.List;

import org.ilmostro.pure.annotation.SignatureParameter;

import org.springframework.core.Conventions;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodArgumentResolver;

/**
 * @author li.bowei
 */
public class SignatureHandlerMethodArgumentResolver extends AbstractMessageConverterMethodArgumentResolver {

	public SignatureHandlerMethodArgumentResolver(List<HttpMessageConverter<?>> converters) {
		super(converters);
	}

	@Override
	public boolean supportsParameter(@NonNull MethodParameter parameter) {
		return parameter.hasParameterAnnotation(SignatureParameter.class);
	}

	@Override
	public Object resolveArgument(@NonNull MethodParameter parameter,
			ModelAndViewContainer container,
			@NonNull NativeWebRequest request,
			WebDataBinderFactory factory) throws Exception {
		parameter = parameter.nestedIfOptional();
		Object arg = readWithMessageConverters(request, parameter, parameter.getNestedGenericParameterType());
		String name = Conventions.getVariableNameForParameter(parameter);

		if (factory != null) {
			WebDataBinder binder = factory.createBinder(request, arg, name);
			if (arg != null) {
				validateIfApplicable(binder, parameter);
				if (binder.getBindingResult().hasErrors() && isBindExceptionRequired(binder, parameter)) {
					throw new MethodArgumentNotValidException(parameter, binder.getBindingResult());
				}
			}
			if (container != null) {
				container.addAttribute(BindingResult.MODEL_KEY_PREFIX + name, binder.getBindingResult());
			}
		}
		return arg;
	}
}
