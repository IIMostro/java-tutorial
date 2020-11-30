package org.ilmostro.start.utils;

/**
 * @author li.bowei
 * @date 2020/10/29 16:06
 */
public class ThreadLocalUtils {

    private static final ThreadLocal<Integer> THREAD_LOCAL = new ThreadLocal<>();

    public static void clean(){
        THREAD_LOCAL.remove();
    }

    public static Integer get(){
        return THREAD_LOCAL.get();
    }

    public static void set(Integer code){
        THREAD_LOCAL.set(code);
    }
}
