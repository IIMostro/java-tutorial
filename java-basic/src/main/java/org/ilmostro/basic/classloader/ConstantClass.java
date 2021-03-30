package org.ilmostro.basic.classloader;

/**
 * @author li.bowei
 **/
public class ConstantClass {

    public static final String KEY = "key";

    static{
        System.out.println("this is constant class static function!");
    }
}
