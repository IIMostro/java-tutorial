package org.ilmostro.pure.configuration;

import io.vertx.core.Handler;
import io.vertx.ext.web.client.impl.ClientPhase;
import io.vertx.ext.web.client.impl.HttpContext;
import io.vertx.ext.web.client.impl.MultipartFormUpload;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.MDC;
import org.springframework.http.MediaType;

import java.util.Objects;
import java.util.UUID;

@Slf4j
public class WebClientLoggingInterceptor implements Handler<HttpContext<?>> {

    private final String trace;
    private static final String TRACE_ID = "TraceId";

    public WebClientLoggingInterceptor(String trace) {
        this.trace = trace;
    }

    public WebClientLoggingInterceptor(){
        this(TRACE_ID);
    }


    @Override
    public void handle(HttpContext<?> event) {
        final var phase = event.phase();
        // 此时的线程还在main中
        if (phase.equals(ClientPhase.CREATE_REQUEST)){
            final var traceId = MDC.get(trace);
            event.set(trace, StringUtils.isBlank(traceId) ? UUID.randomUUID().toString() : traceId);
        }
        if (phase.equals(ClientPhase.SEND_REQUEST)) {
            event.set("TIME", System.currentTimeMillis());
            printRequest(event);
        }
        if (phase.equals(ClientPhase.DISPATCH_RESPONSE)) {
            printResponse(event);
        }
        event.next();
    }

    private void printRequest(HttpContext<?> event){
        try{
            MDC.put(trace, event.get(trace));
            log.info(" --> {} {}", event.requestOptions().getMethod(), event.requestOptions().getHost());
            final var body = event.body();
            if (Objects.nonNull(body)) {
                final var contentType = event.requestOptions().getHeaders().get("Content-Type");
                log.info(" Content-Type: {}", contentType);
                log.info(" Content-Length: {}", event.requestOptions().getHeaders().get("Content-Length"));
                if (StringUtils.equals(contentType, MediaType.APPLICATION_FORM_URLENCODED_VALUE) &&
                        body instanceof MultipartFormUpload) {
                    final var field = FieldUtils.getDeclaredField(MultipartFormUpload.class, "encoder", true);
                    try {
                        final Object o = field.get(body);
                        final var declaredField = FieldUtils.getDeclaredField(o.getClass(), "bodyListDatas", true);
                        final var bodyListDatas = declaredField.get(o);
                        log.info(" Body: {}", bodyListDatas);
                    } catch (IllegalAccessException e) {
                        // ignore
                    }
                } else {
                    log.info(" Body: {}", body);
                }
            }
        } catch (Exception ex){
            // ignore
        }
    }

    private  void printResponse(HttpContext<?> event) {
        try {
            MDC.put(trace, event.get(trace));
            log.info(" <-- {} {} {} {}ms", event.response().statusCode(), event.response().statusMessage(), event.requestOptions().getHost(), System.currentTimeMillis() - event.<Long>get("TIME"));
            log.info(" Content-Type: {}", event.response().getHeader("Content-Type"));
            log.info(" Set Cookies: {}", event.response().getHeader("Set-Cookie"));
            log.info(" Content-Length: {}", event.response().getHeader("Content-Length"));
            log.info(" Body: {}", event.response().bodyAsString());
        } catch (Exception ex) {
            // ignore
        }
    }
}
