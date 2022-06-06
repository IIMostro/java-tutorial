package org.ilmostro.basic.reactor;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.util.annotation.NonNull;

import java.util.concurrent.TimeUnit;

/**
 * @author li.bowei
 */
@Slf4j
public class SampleSubscriber<T> extends BaseSubscriber<T> {

    @SneakyThrows
    @Override
    protected void hookOnSubscribe(@NonNull Subscription subscription) {
        log.info("hook on subscribe");
        TimeUnit.SECONDS.sleep(1);
        request(1);
    }

    @SneakyThrows
    @Override
    protected void hookOnNext(@NonNull T value) {
        log.info("hook on next");
        TimeUnit.SECONDS.sleep(1);
        request(1);
        log.info("{}", value);
    }
}
