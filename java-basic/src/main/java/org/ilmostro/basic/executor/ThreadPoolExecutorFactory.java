package org.ilmostro.basic.executor;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author li.bowei
 * @date 2020/7/23 16:40
 */
public class ThreadPoolExecutorFactory {
    public static ThreadPoolExecutor get(){
        return new ThreadPoolExecutor(5, 10, 10, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());
    }
}
