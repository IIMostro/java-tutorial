package org.ilmostro.test.template.function.supplier;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.util.function.LongSupplier;
import java.util.function.Supplier;

import static org.junit.Assert.assertNotNull;

/**
 * @author li.bowei on 2019-10-12.
 * @description
 */
@Slf4j
public class SupplierMethodTest {

    private Supplier<Integer> supplier;
    private LongSupplier longSupplier;

    @Before
    public void before(){
        SupplierMethod method = new SupplierMethod();
        this.supplier = method.getSupplier();
        this.longSupplier = method.getLongSupplier();
    }

    @Test
    public void getSupplier() {
        Integer var1 = supplier.get();
        log.info("supplier produce is {}", var1);
        assertNotNull(var1);
    }

    @Test
    public void getLongSupplier() {
        long var1 = longSupplier.getAsLong();
        log.info("long supplier produce is {}", var1);
        assertNotNull(var1);
    }
}