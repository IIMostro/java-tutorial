package org.ilmostro.basic.executor;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cn.hutool.core.thread.ExecutorBuilder;
import cn.hutool.core.thread.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author li.bowei
 * @date 2020/7/23 16:40
 */
@Slf4j
public class ThreadPoolExecutorFactory {


    public static ThreadPoolExecutor get(boolean daemon) {
        return get(5, daemon);
    }

    public static ThreadPoolExecutor get(int core, boolean daemon) {
        return ExecutorBuilder.create()
                .setCorePoolSize(core)
                .setMaxPoolSize(10)
                .setKeepAliveTime(30, TimeUnit.SECONDS)
                .setWorkQueue(new LinkedBlockingQueue<>(1000))
                .setThreadFactory(ThreadFactoryBuilder.create().setDaemon(daemon)
                        .setNamePrefix("customize-").build())
                .setHandler((r, executor) -> {

                })
                .build();
    }
}
