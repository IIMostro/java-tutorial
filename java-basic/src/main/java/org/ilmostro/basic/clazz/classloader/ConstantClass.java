package org.ilmostro.basic.clazz.classloader;

/**
 * @author li.bowei
 **/
public class ConstantClass {

    public final String key = "key";

    static{
        System.out.println("this is constant class static function!");
    }

    public String getKEY() {
        return key;
    }
}
