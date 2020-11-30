package org.ilmostro.test.template.function.predicate;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.function.IntPredicate;
import java.util.function.Predicate;

import static org.junit.Assert.assertTrue;

/**
 * @author li.bowei on 2019-10-12.
 * @description
 */
@Slf4j
public class PredicateMethodTest {

    @Test
    public void getPredicate(){
        Predicate<Object> predicate = new PredicateMethod().getPredicate();
        Object var1 = null;
        boolean test = predicate.test(var1);
        log.info("predicate var1={} test={}", var1, test);
        assertTrue(test);
    }

    @Test
    public void getIntPredicate(){
        IntPredicate predicate = new PredicateMethod().getIntPredicate();
        Integer var1 = 1;
        boolean test = predicate.test(var1);
        log.info("int predicate var1={}, test={}", var1, test);
        assertTrue(test);
    }

}