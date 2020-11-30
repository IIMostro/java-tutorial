package org.ilmostro.test.template.optional;

import org.junit.Before;
import org.junit.Test;

/**
 * @author li.bowei on 2019-10-12.
 * @description
 */
public class OptionalMethodTest {

    private OptionalMethod method;

    @Before
    public void before(){
        this.method = new OptionalMethod();
    }

    @Test
    public void simple() {
        method.simple();
    }
}