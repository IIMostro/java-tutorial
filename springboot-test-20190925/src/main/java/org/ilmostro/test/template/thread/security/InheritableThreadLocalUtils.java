package org.ilmostro.test.template.thread.security;

/**
 * @author li.bowei on 2019-10-11.
 * @description InheritableThreadLocal工具类
 */
public class InheritableThreadLocalUtils {

    /**
     *  重点为ThreadLocalMap<Key,Value> key为每个线程的唯一标识,value为data
     */
    private static final InheritableThreadLocal<Integer> THREAD_LOCAL = new InheritableThreadLocal<>();

    /**
     * 向ThreadLocalMap中设置值
     * @param data 线程中的数据
     */
    public static void set(Integer data){
        THREAD_LOCAL.set(data);
    }

    /**
     * 获取ThreadLocalMap中的值
     * @return 重点为ThreadLocalMap中的数据
     */
    public static Integer get(){
        return THREAD_LOCAL.get();
    }

    /**
     * 移除里面的数据
     */
    public static void remove(){
        THREAD_LOCAL.remove();
    }
}
