package org.ilmostro.disruptor.configuration;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.ilmostro.disruptor.entity.ElementEventFactory;
import org.ilmostro.disruptor.entity.SheetElement;
import org.ilmostro.disruptor.service.ProcessService;
import org.ilmostro.disruptor.service.SheetService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author li.bowei
 */
@Configuration
public class DisruptorConfiguration {

    @Bean
    public ThreadFactory disruptorThreadFactory(){
        return new DisruptorThreadFactory();
    }

    @Bean
    public Disruptor<SheetElement> disruptor(ThreadFactory disruptorThreadFactory,
                                             SheetService service,
                                             ProcessService processService){
        BlockingWaitStrategy strategy = new BlockingWaitStrategy();
        int bufferSize = 32;
        ElementEventFactory factory = new ElementEventFactory();
        Disruptor<SheetElement> disruptor = new Disruptor<>(factory, bufferSize, disruptorThreadFactory, ProducerType.SINGLE, strategy);
        disruptor.handleEventsWith(service).then(processService);
        disruptor.start();
        return disruptor;
    }

    public static class DisruptorThreadFactory implements ThreadFactory{
        private final LongAdder counter = new LongAdder();
        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable, "disruptor-task-" + counter.intValue());
            counter.increment();
            return thread;
        }
    }
}
