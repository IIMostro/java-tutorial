package org.ilmostro.webflux.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.ilmostro.webflux.result.BasicConst;
import org.ilmostro.webflux.result.BasicResult;
import org.reactivestreams.Publisher;
import org.springframework.core.ResolvableType;
import org.springframework.http.MediaType;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.*;

/**
 * @author li.bowei
 * @date 2020/12/2 17:16
 */
@Component
public class JsonHttpMessageWriter implements HttpMessageWriter<Map<String, Object>> {

    private final ObjectMapper mapper;

    public JsonHttpMessageWriter(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @NonNull
    @Override
    public List<MediaType> getWritableMediaTypes() {
        return Collections.singletonList(MediaType.APPLICATION_JSON);
    }

    @Override
    public boolean canWrite(@NonNull ResolvableType elementType, MediaType mediaType) {
        return MediaType.APPLICATION_JSON.includes(mediaType);
    }

    @NonNull
    @Override
    public Mono<Void> write(@NonNull Publisher<? extends Map<String, Object>> inputStream,
                            @NonNull ResolvableType elementType,
                            MediaType mediaType,
                            @NonNull ReactiveHttpOutputMessage message,
                            @NonNull Map<String, Object> hints) {
        return Mono.from(inputStream).flatMap(m -> message.writeWith(Mono.just(message.bufferFactory().wrap(transform2Json(m).getBytes()))));
    }

    @SneakyThrows
    private String transform2Json(Map<String, Object> attributes) {
        BasicResult<Object> result = BasicResult.getInstance(attributes);
        return mapper.writeValueAsString(result);
    }
}
