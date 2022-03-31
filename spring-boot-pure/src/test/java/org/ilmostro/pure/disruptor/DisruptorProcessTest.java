package org.ilmostro.pure.disruptor;

import com.lmax.disruptor.BatchEventProcessor;
import com.lmax.disruptor.dsl.Disruptor;
import org.ilmostro.pure.configuration.DisruptorConfiguration;
import org.ilmostro.pure.disruptor.order.MessageEventHandler;
import org.ilmostro.pure.domain.GoodsElement;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author li.bowei
 */
public class DisruptorProcessTest {

    Disruptor<GoodsElement> disruptor = DisruptorConfiguration.getInstance();

    @Test
    public void process() throws Exception {
        disruptor.handleEventsWith(new BatchEventProcessor<>(disruptor.getRingBuffer(), disruptor.getRingBuffer().newBarrier(), new MessageEventHandler()));
        disruptor.start();
        for (int i = 0; i < 10; i++) {
            final int fi = i;
            disruptor.publishEvent(((event, sequence) -> event.setId(fi)));
        }
        TimeUnit.SECONDS.sleep(2);
    }

    @Test
    public void factory() throws Exception {
        disruptor.handleEventsWith(new CacheEventProcessorFactory());
        disruptor.start();
        for (int i = 0; i < 10; i++) {
            final int fi = i;
            disruptor.publishEvent(((event, sequence) -> event.setId(fi)));
        }
        TimeUnit.SECONDS.sleep(2);
    }
}
