package org.ilmostro.basic.executor;

import cn.hutool.core.thread.ExecutorBuilder;
import cn.hutool.core.thread.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author li.bowei
 * @date 2020/7/23 16:40
 */
@Slf4j
public class ThreadPoolExecutorFactory {


    public static ThreadPoolExecutor get(boolean daemon) {
        return ExecutorBuilder.create()
                .setCorePoolSize(5)
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
