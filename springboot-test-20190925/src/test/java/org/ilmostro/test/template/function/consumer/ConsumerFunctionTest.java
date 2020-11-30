package org.ilmostro.test.template.function.consumer;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.ObjDoubleConsumer;

/**
 * @author li.bowei on 2019-10-12.
 * @description
 */
public class ConsumerFunctionTest {

    @Test
    public void getConsumer(){
        Consumer<String> consumer = new ConsumerMethod().getConsumer();
        List<String> list = Arrays.asList("1", "2", "3");
        list.forEach(consumer);
    }

    @Test
    public void getBiConsumer(){
        BiConsumer<String, String> consumer = new ConsumerMethod().getBiConsumer();
        consumer.accept("hello", " world");
    }

    @Test
    public void getObjDoubleConsumer(){
        ObjDoubleConsumer consumer = new ConsumerMethod().getObjDoubleConsumer();
        //noinspection unchecked
        consumer.accept("hello, world!", 2.0);
    }
}