package org.ilmostro.basic.vavr;

import io.vavr.concurrent.Future;
import io.vavr.concurrent.Promise;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.function.Consumer;

/**
 * @author li.bowei
 */
@Slf4j
public class FutureTest {

    @Test
    public void test() {
        Promise<Object> promise = Promise.make();
        supplier("this", v1 -> {
            if (v1.equals("1")) {
                promise.success("success!");
            } else {
                promise.failure(new RuntimeException());
            }
        });
        promise.future();
    }

    private static <T> Future<String> supplier(T address, Consumer<T> consumer) {
        consumer.accept(address);
        return Future.successful("success!");
    }

    @Test
    public void test1() {
        Future<Object> f1 = Future.of(() -> {
            log.info("1");
            return null;
        });

        Future<Object> f2 = Future.of(() -> {
            log.info("2");
            return null;
        });

        Future<Object> f3 = Future.of(() -> {
            log.info("3");
            return null;
        });

        Future<Object> objects = f1.zipWith(f2, (v1, v2) -> null).zipWith(f3, (v1, v2) -> null);
        objects.await();
        objects.await();
    }
}
