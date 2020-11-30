package org.ilmostro.basic.function;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author li.bowei
 * @date 2020/7/23 10:51
 */
@Slf4j
public class SupplierTest {

    /**
     * 简单的生产函数
     */
    @Test
    public void simple(){
        Supplier<Integer> supplier = () -> new Random().nextInt();
        log.info("simple supplier, producer number: {}", supplier.get());
    }

    /**
     * 流生成函数
     */
    @Test
    public void streamSupplier(){
        Stream.generate(() -> new Random().nextInt()).limit(1000).forEach(System.out::println);
    }
}
