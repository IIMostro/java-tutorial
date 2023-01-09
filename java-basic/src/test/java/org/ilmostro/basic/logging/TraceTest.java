package org.ilmostro.basic.logging;

import org.ilmostro.basic.executor.ThreadPoolExecutorFactory;
import org.ilmostro.basic.reactor.SchedulerMdcProxyDecorator;
import org.junit.Test;
import org.nlpcn.commons.lang.occurrence.Count;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author li.bowei
 */
public class TraceTest {

    private static final Logger logger = LoggerFactory.getLogger(TraceTest.class);

    @Test
    public void test(){
        String curr = UUID.randomUUID().toString();
        MDC.put("TraceId", curr);
        MDC.put("SpanId", curr);
        logger.info("sss");
    }

    @Test
    public void mono_log_test() throws Exception{
        for (int i = 0; i < 10; i++) {
            String curr = UUID.randomUUID().toString();
            MDC.put("TraceId", curr);
            MDC.put("SpanId", curr);
            logger.info("current trace id:{}", curr);
            Mono.fromRunnable(ThreadPoolExecutorFactory.wrap(this::mono_log))
                    .subscribeOn(Schedulers.fromExecutor(ThreadPoolExecutorFactory.get("mono-log")))
                    .subscribe();
        }
        TimeUnit.SECONDS.sleep(1);
    }

    private void mono_log(){
        logger.info("thread name:{}", Thread.currentThread().getName());
    }


    @Test
    public void mono_log_decorator() throws Exception{
        Schedulers.addExecutorServiceDecorator("mdc", new SchedulerMdcProxyDecorator());
        final Scheduler scheduler = Schedulers.fromExecutor(ThreadPoolExecutorFactory.get("mono-log"));
        for (int i = 0; i < 10; i++) {
            String curr = UUID.randomUUID().toString();
            MDC.put("TraceId", curr);
            MDC.put("SpanId", curr);
            logger.info("current trace id:{}", curr);
            Mono.fromRunnable(this::mono_log)
                    .subscribeOn(scheduler)
                    .subscribe();
        }
        TimeUnit.SECONDS.sleep(1);
    }

}
