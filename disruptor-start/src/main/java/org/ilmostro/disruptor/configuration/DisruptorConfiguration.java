package org.ilmostro.disruptor.configuration;

import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.ilmostro.disruptor.entity.ElementEventFactory;
import org.ilmostro.disruptor.entity.GoodsElement;
import org.ilmostro.disruptor.mulit.ConsumerEventHandler;
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
    public Disruptor<GoodsElement> disruptor(ThreadFactory disruptorThreadFactory){
        WaitStrategy strategy = new YieldingWaitStrategy();
        int bufferSize = 16;
        ElementEventFactory factory = new ElementEventFactory();
        Disruptor<GoodsElement> disruptor = new Disruptor<>(factory, bufferSize, disruptorThreadFactory, ProducerType.SINGLE, strategy);

        WorkHandler<GoodsElement>[] handlers = new ConsumerEventHandler[10];
        for (int i = 0; i < handlers.length; i++) {
            handlers[i] = new ConsumerEventHandler("C" + i);
        }
        disruptor.handleEventsWithWorkerPool(handlers);
        disruptor.handleEventsWith(new ProcessService());
        disruptor.setDefaultExceptionHandler(new ExceptionHandler<GoodsElement>() {
            @Override
            public void handleEventException(Throwable ex, long sequence, GoodsElement event) {

            }

            @Override
            public void handleOnStartException(Throwable ex) {

            }

            @Override
            public void handleOnShutdownException(Throwable ex) {

            }
        });
        disruptor.start();
        return disruptor;
    }
}
