package org.ilmostro.test.template.function.consumer;

import lombok.extern.slf4j.Slf4j;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.ObjDoubleConsumer;

/**
 * @author li.bowei on 2019-10-12.
 * @description
 */
@Slf4j
public class ConsumerMethod {

    /**
     * 消费者函数,传入一个参数，没有返回的
     * 常用子类DoubleConsumer，IntConsumer，LongConsumer
     * 基本使用方法一致
     *
     * @return 消费者函数
     * @see DoubleConsumer
     * @see java.util.function.IntConsumer
     * @see java.util.function.LongConsumer
     */
    public Consumer<String> getConsumer() {
        return log::info;
    }

    /**
     * 消费两个String
     *
     * @return 消费函数
     */
    public BiConsumer<String, String> getBiConsumer() {
        return (var1, var2) -> log.info("var1 append var2 is {}", var1 + var2);
    }

    /**
     * 消费一个double类型的参数
     *
     * @return DoubleConsumer
     */
    public DoubleConsumer getDoubleConsumer() {
        return System.out::println;
    }

    /**
     * 消费一个object和一个double参数
     *
     * @return ObjDoubleConsumer
     */
    public ObjDoubleConsumer getObjDoubleConsumer(){
        return (o, value) -> log.info("object={}, double={}", o, value);
    }
}
