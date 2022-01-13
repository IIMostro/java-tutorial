package org.ilmostro.pure.disruptor;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.EventProcessorFactory;
import org.ilmostro.pure.domain.GoodsElement;

/**
 * @author li.bowei
 */
public class ConsumerEventFactory implements EventProcessorFactory<GoodsElement> {

    @Override
    public EventProcessor createEventProcessor(RingBuffer<GoodsElement> ringBuffer, Sequence[] sequences) {
        SequenceBarrier barrier = ringBuffer.newBarrier(sequences);
        return new BatchEventProcessor<>(ringBuffer, barrier, new ConsumerEventHandler("C1"));
    }
}
