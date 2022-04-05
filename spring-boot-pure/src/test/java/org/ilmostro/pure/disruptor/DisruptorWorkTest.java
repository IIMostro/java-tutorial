package org.ilmostro.pure.disruptor;

import com.lmax.disruptor.WorkerPool;
import com.lmax.disruptor.dsl.Disruptor;
import org.ilmostro.pure.configuration.DisruptorConfiguration;
import org.ilmostro.pure.domain.GoodsElement;
import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *
 * 新版本居然删除了WorkHandle, WorkProcessor, WorkPool。包括了Disruptor的handleEventsWithWorkerPool
 *
 * @author li.bowei
 */
@Deprecated
public class DisruptorWorkTest {

    Disruptor<GoodsElement> disruptor = DisruptorConfiguration.getInstance();


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
}
