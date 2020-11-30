package org.ilmostro.test.utils;

import org.ilmostro.test.domian.ThreadEntity;

/**
 * @author li.bowei on 2019-10-21.
 * @description
 */
public class InheritableUtils {

    private static final InheritableThreadLocal<ThreadEntity> var1 = new InheritableThreadLocal<>();

    public static void set(ThreadEntity var2){
        var1.set(var2);
    }

    public static ThreadEntity get(){
        return var1.get();
    }

    public static void remove(){
        var1.remove();
    }
}
