package org.ilmostro.pure.support;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.ilmostro.pure.annotation.RequestModel;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.lang.NonNull;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

/**
 * @author li.bowei
 */
@Slf4j
public class RequestModelArgumentResolver extends RequestResponseBodyMethodProcessor {

	public RequestModelArgumentResolver(ObjectMapper objectMapper) {
		super(Arrays.asList(new CryptographyJsonHttpMessageConverter(objectMapper),
				new CryptographyFormHttpMessageConverter()));
	}

	private static final class CryptographyJsonHttpMessageConverter extends MappingJackson2HttpMessageConverter {

		private CryptographyJsonHttpMessageConverter(ObjectMapper objectMapper) {
			super(objectMapper);
		}

		@Override
		@NonNull
		public Object read(@NonNull Type type, Class<?> contextClass, @NonNull HttpInputMessage inputMessage) throws HttpMessageNotReadableException, IOException {
			final InputStream body = inputMessage.getBody();
			final String json = IOUtils.readLines(body).stream().reduce(String::concat).orElse("");
			final JSONObject jsonObject = JSON.parseObject(json);
			for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
				return JSON.parseObject(entry.getValue().toString(), type);
//				try {
////					final String decrypt = utils.decrypt(entry.getValue().toString());
//
//				}
//				catch (IllegalBlockSizeException | BadPaddingException e) {
//					throw new RuntimeException(e);
//				}
			}
			throw new HttpMessageConversionException("json cryptography error");
		}
	}


	private static final class CryptographyFormHttpMessageConverter implements HttpMessageConverter<Object> {

		private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
		private static final List<MediaType> converters = Collections.singletonList(MediaType.APPLICATION_FORM_URLENCODED);

		@Override
		public boolean canRead(@NonNull Class<?> clazz, MediaType mediaType) {
			for (MediaType supportedMediaType : converters) {
				if (supportedMediaType.equals(mediaType)) return true;
			}
			return false;
		}

		@Override
		public boolean canWrite(@NonNull Class<?> clazz, MediaType mediaType) {
			return false;
		}

		@Override
		@NonNull
		public List<MediaType> getSupportedMediaTypes() {
			return converters;
		}

		@Override
		@NonNull
		public Object read(@NonNull Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
			MediaType contentType = inputMessage.getHeaders().getContentType();
			Charset charset = (contentType != null && contentType.getCharset() != null ?
					contentType.getCharset() : DEFAULT_CHARSET);
			String body = StreamUtils.copyToString(inputMessage.getBody(), charset);
			String[] pairs = org.springframework.util.StringUtils.tokenizeToStringArray(body, "&");
			if (pairs.length > 1) {
				log.error("cryptography parameter too match parameter:[{}]", (Object) pairs);
				throw new RuntimeException();
			}
			for (String pair : pairs) {
				int idx = pair.indexOf('=');
				if (idx == -1) {
					throw new HttpMessageConversionException("form cryptography error");
				}
				else {
					String value = URLDecoder.decode(pair.substring(idx + 1), charset.name());
					return JSON.parseObject(value, clazz);
//					try {
////						final String decrypt = utils.decrypt(value);
//
//					}
//					catch (IllegalBlockSizeException | BadPaddingException e) {
//						throw new HttpMessageConversionException("form cryptography error");
//					}
				}
			}
			throw new HttpMessageConversionException("form cryptography error");
		}

		@Override
		public void write(@NonNull Object o, MediaType contentType, @NonNull HttpOutputMessage outputMessage) throws HttpMessageNotWritableException {
			throw new HttpMessageNotWritableException("not support!");
		}
	}

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
