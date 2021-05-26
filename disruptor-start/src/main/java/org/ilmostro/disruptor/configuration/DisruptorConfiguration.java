package org.ilmostro.disruptor.configuration;

import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.ilmostro.disruptor.entity.ElementEventFactory;
import org.ilmostro.disruptor.entity.GoodsElement;
import org.ilmostro.disruptor.service.CacheGoodsElementService;
import org.ilmostro.disruptor.service.DataSourceGoodsService;
import org.ilmostro.disruptor.service.ProcessService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ThreadFactory;

/**
 * @author li.bowei
 */
@Configuration
public class DisruptorConfiguration {

    @Bean
    public ThreadFactory disruptorThreadFactory(){
        return new BasicThreadFactory.Builder().namingPattern("disruptor-task-%d").daemon(false).build();
    }

    @Bean
    public Disruptor<GoodsElement> disruptor(ThreadFactory disruptorThreadFactory,
                                             DataSourceGoodsService service,
                                             ProcessService processService,
                                             CacheGoodsElementService cacheGoodsElementService){
        WaitStrategy strategy = new YieldingWaitStrategy();
        int bufferSize = 16;
        ElementEventFactory factory = new ElementEventFactory();
        Disruptor<GoodsElement> disruptor = new Disruptor<>(factory, bufferSize, disruptorThreadFactory, ProducerType.SINGLE, strategy);
        disruptor.handleEventsWith(service, cacheGoodsElementService).then(processService);
        disruptor.start();
        return disruptor;
    }

//    public static class DisruptorThreadFactory implements ThreadFactory{
//        private final LongAdder counter = new LongAdder();
//        @Override
//        public Thread newThread(Runnable runnable) {
//            Thread thread = new Thread(runnable, "disruptor-task-" + counter.intValue());
//            counter.increment();
//            return thread;
//        }
//    }
}
