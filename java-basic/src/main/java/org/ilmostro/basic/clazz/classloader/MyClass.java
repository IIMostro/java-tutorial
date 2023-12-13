package org.ilmostro.basic.clazz.classloader;

import lombok.extern.slf4j.Slf4j;

/**
 * @author li.bowei
 **/
@Slf4j
public class MyClass {

    static {
        log.info("this is static block!");

    }

    private static final MyClass myclass = new MyClass();

    private static int a = 0;
    private static int b;

    public MyClass() {
        log.info("this is constructor");
        a++;
        b++;
    }

    public static MyClass getInstance() {
        return myclass;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    @Override
    public String toString() {
        return "MyClass{a =" + a + ", b=" + b + "}";
    }
}
