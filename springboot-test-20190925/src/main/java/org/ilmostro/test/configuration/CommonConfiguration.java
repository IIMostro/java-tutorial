package org.ilmostro.test.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

/**
 * @author li.bowei on 2019-09-26.
 * @description
 */
@Configuration
public class CommonConfiguration {

    /**
     * forkJoinPool
     *
     * @return
     */
    @Bean
    public ForkJoinPool forkJoinPool() {
        return new ForkJoinPool(20);
    }

    /**
     * 线程池定义
     *
     * @return 返回线程池
     */
    @Bean(name = "applicationCustomThread")
    public Executor getExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(15);
        executor.setQueueCapacity(25);
        executor.setThreadNamePrefix("ilmostro-thread-");
        return executor;
    }

}
