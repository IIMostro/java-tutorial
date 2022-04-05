package org.ilmostro.pure.disruptor;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.lmax.disruptor.BatchEventProcessor;
import com.lmax.disruptor.EventProcessor;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.Sequence;
import com.lmax.disruptor.dsl.EventProcessorFactory;
import lombok.SneakyThrows;
import org.ilmostro.pure.disruptor.order.MessageEventHandler;
import org.ilmostro.pure.domain.GoodsElement;

import java.util.concurrent.TimeUnit;

/**
 * @author li.bowei
 */
public class CacheEventProcessorFactory implements EventProcessorFactory<GoodsElement> {

    private static final Cache<Integer, EventProcessor> cache = CacheBuilder.newBuilder()
            .initialCapacity(10)
            .maximumSize(10)
            .expireAfterAccess(10, TimeUnit.MINUTES)
            .build();

    @SneakyThrows
    @Override
    public EventProcessor createEventProcessor(RingBuffer<GoodsElement> ringBuffer, Sequence[] barrierSequences) {
        int cluster = (int) (ringBuffer.getCursor() % 10);
        MessageEventHandler handler = new MessageEventHandler();
        return cache.get(cluster, () -> {
            BatchEventProcessor<GoodsElement> processor = new BatchEventProcessor<>(ringBuffer, ringBuffer.newBarrier(barrierSequences), handler);
            processor.setExceptionHandler(handler);
            return processor;
        });
    }
}
