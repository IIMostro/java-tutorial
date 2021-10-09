package org.ilmostro.basic.vavr;

import io.vavr.concurrent.Future;
import io.vavr.concurrent.Promise;
import org.junit.Test;

import java.util.function.Consumer;

/**
 * @author li.bowei
 */
public class FutureTest {

    @Test
    public void test(){
        Promise<Object> promise = Promise.make();
        supplier("this", v1 -> {
            if(v1.equals("1")){
                promise.success("success!");
            }else{
                promise.failure(new RuntimeException());
            }
        });
        promise.future();
    }

    private static <T> Future<String> supplier(T address, Consumer<T> consumer){
        consumer.accept(address);
        return Future.successful("success!");
    }
}
