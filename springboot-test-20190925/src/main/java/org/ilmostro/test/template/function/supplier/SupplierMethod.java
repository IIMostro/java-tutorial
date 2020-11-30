package org.ilmostro.test.template.function.supplier;

import java.util.Random;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

/**
 * @author li.bowei on 2019-10-12.
 * @description
 */
public class SupplierMethod {

    /**
     * 生成一个随机的Integer
     *
     * @return 生产者函数
     */
    public Supplier<Integer> getSupplier() {
        return () -> new Random().nextInt();
    }

    /**
     * 生产一个随机的Long，
     *
     * @return 函数
     */
    public LongSupplier getLongSupplier() {
        return () -> new Random().nextLong();
    }
}
