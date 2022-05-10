package org.ilmostro.pure.disruptor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkerPool;
import com.lmax.disruptor.dsl.Disruptor;
import org.ilmostro.pure.configuration.DisruptorConfiguration;
import org.ilmostro.pure.domain.ElementEventFactory;
import org.ilmostro.pure.domain.GoodsElement;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * 新版本居然删除了WorkHandle, WorkProcessor, WorkPool。包括了Disruptor的handleEventsWithWorkerPool
 *
 * @author li.bowei
 */
@Deprecated
public class DisruptorWorkTest {

    Disruptor<GoodsElement> disruptor = DisruptorConfiguration.getInstance(ElementEventFactory::new);
    private static final int count = 10;


    @Test
    public void multiple() throws Exception{
        Consumer[] consumers = new Consumer[10];
        for (int i = 0; i < consumers.length; i++) {
            consumers[i] = new Consumer("C-" + i);
        }
        WorkerPool<GoodsElement> pool = new WorkerPool<>(disruptor.getRingBuffer(), disruptor.getRingBuffer().newBarrier(), consumers[0], consumers);
        disruptor.getRingBuffer().addGatingSequences(pool.getWorkerSequences());
        pool.start(Executors.newFixedThreadPool(10));
        disruptor.start();

        for (int i = 0; i < 10; i++) {
            final int fi = i;
            disruptor.publishEvent(((event, sequence) -> event.setId(fi)));
        }

        TimeUnit.SECONDS.sleep(3);
    }

    @Test
    public void parallel() throws Exception{
        List<ParallelEventHandler> handlers = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            handlers.add(new ParallelEventHandler(count, i));
        }
        disruptor.handleEventsWith(handlers.toArray(new ParallelEventHandler[0]));
        disruptor.start();
        for (int i = 0; i < 100; i++) {
            final int fi = i;
            disruptor.publishEvent(((event, sequence) -> event.setId(fi)));
        }
        TimeUnit.SECONDS.sleep(3);
    }

    static class ParallelEventHandler implements EventHandler<GoodsElement>{

        private static final Logger logger = LoggerFactory.getLogger(ParallelEventHandler.class);

        private final int mask;
        private final int ordinal;

        ParallelEventHandler(int mask, int ordinal) {
            this.mask = mask;
            this.ordinal = ordinal;
        }

        @Override
        public void onEvent(GoodsElement event, long sequence, boolean endOfBatch) throws Exception {
            if ((sequence & mask) != ordinal){
                return;
            }
            LockSupport.parkNanos(1);
            logger.info("current thread:{}, event:{}", Thread.currentThread().getName(), event);
        }
    }
}
