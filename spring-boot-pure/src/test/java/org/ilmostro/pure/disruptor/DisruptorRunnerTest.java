package org.ilmostro.pure.disruptor;

import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import org.ilmostro.pure.configuration.DisruptorConfiguration;
import org.ilmostro.pure.domain.GoodsElement;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author li.bowei
 */
public class DisruptorRunnerTest {

    @Test
    public void rhombus() throws InterruptedException {
        Disruptor<GoodsElement> disruptor = DisruptorConfiguration.getInstance();

        disruptor.handleEventsWith(new ConsumerEventHandler("consumer-handler-1"));

        EventHandlerGroup<GoodsElement> group = disruptor.handleEventsWith(new ConsumerEventHandler("consumer-handler-2"));
        EventHandlerGroup<GoodsElement> and = disruptor.handleEventsWith(new ConsumerEventHandler("consumer-and-1"));
        group.and(and);
        group.then(new ConsumerEventHandler("consumer-then-1"));

        disruptor.start();
        disruptor.publishEvent(((event, sequence) -> event.setId(1)));
        TimeUnit.SECONDS.sleep(1);
    }

    @Test
    public void consumer() throws InterruptedException{



    }
}
