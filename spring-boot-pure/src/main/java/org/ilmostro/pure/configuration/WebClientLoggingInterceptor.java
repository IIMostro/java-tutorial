package org.ilmostro.pure.configuration;

import io.vertx.core.Handler;
import io.vertx.ext.web.client.impl.ClientPhase;
import io.vertx.ext.web.client.impl.HttpContext;
import io.vertx.ext.web.client.impl.MultipartFormUpload;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.util.Objects;

@Slf4j
public class WebClientLoggingInterceptor implements Handler<HttpContext<?>> {
    @Override
    public void handle(HttpContext<?> event) {
        final var phase = event.phase();
        if (phase.equals(ClientPhase.SEND_REQUEST)) {
            log.info(" --> {} {}", event.requestOptions().getMethod(), event.requestOptions().getHost());
            final var body = event.body();
            if (Objects.nonNull(body)) {
                final var contentType = event.requestOptions().getHeaders().get("Content-Type");
                log.info(" Content-Type: {}", contentType);
                log.info(" Content-Length: {}", event.requestOptions().getHeaders().get("Content-Length"));
                if (StringUtils.equals(contentType, "application/x-www-form-urlencoded") &&
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
        }
        if (phase.equals(ClientPhase.DISPATCH_RESPONSE)) {
            log.info(" <-- {} {} {}", event.response().statusCode(), event.response().statusMessage(), event.requestOptions().getHost());
            log.info(" Content-Type: {}", event.response().getHeader("Content-Type"));
            log.info(" Set Cookies: {}", event.response().getHeader("Set-Cookie"));
            log.info(" Content-Length: {}", event.response().getHeader("Content-Length"));
            log.info(" Body: {}", event.response().bodyAsString());
        }
        event.next();
    }
}
