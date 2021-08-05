package org.ilmostro.disruptor.configuration;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import com.lmax.disruptor.dsl.ProducerType;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.ilmostro.disruptor.entity.ElementEventFactory;
import org.ilmostro.disruptor.entity.GoodsElement;
import org.ilmostro.disruptor.mulit.ConsumerEventFactory;
import org.ilmostro.disruptor.mulit.ConsumerEventHandler;
import org.ilmostro.disruptor.mulit.SecondConsumerEventHandler;
import org.ilmostro.disruptor.service.ProcessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @author li.bowei
 */
@Configuration
public class DisruptorConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(DisruptorConfiguration.class);

    private static final ThreadFactory DISRUPTOR_THREAD_FACTORY = new BasicThreadFactory.Builder().namingPattern("disruptor-task-%d").daemon(false).build();
    private static final Executor TASK_EXECUTOR = Executors.newFixedThreadPool(10);

    @Bean
    public Disruptor<GoodsElement> disruptor(){
        Disruptor<GoodsElement> disruptor = getInstance();
        WorkHandler<GoodsElement>[] handlers = new ConsumerEventHandler[10];
        for (int i = 0; i < handlers.length; i++) {
            handlers[i] = new ConsumerEventHandler("C" + i);
        }
        //设置一个10个消费者的消费组
        disruptor.handleEventsWithWorkerPool(handlers);
        disruptor.handleEventsWith(new ProcessService());
        disruptor.start();
        return disruptor;
    }

    private static Disruptor<GoodsElement> getInstance(){
        WaitStrategy strategy = new YieldingWaitStrategy();
        int bufferSize = 16;
        ElementEventFactory factory = new ElementEventFactory();
        Disruptor<GoodsElement> disruptor = new  Disruptor<>(factory, bufferSize, DISRUPTOR_THREAD_FACTORY, ProducerType.SINGLE, strategy);
        disruptor.setDefaultExceptionHandler(new ExceptionHandler<GoodsElement>() {
            @Override
            public void handleEventException(Throwable ex, long sequence, GoodsElement event) {
                logger.error("sequence:[{}], event data:[{}], error message:[{}]", sequence, event, ex.getMessage());
            }

            @Override
            public void handleOnStartException(Throwable ex) {
                logger.error("disruptor start error, message:[{}]", ex.getMessage());
            }

            @Override
            public void handleOnShutdownException(Throwable ex) {
                logger.error("disruptor shutdown error, message:[{}]", ex.getMessage());
            }
        });
        return disruptor;
    }

    @Bean
    public Disruptor<GoodsElement> serialWorkPool(){
        Disruptor<GoodsElement> disruptor = getInstance();
        WorkHandler<GoodsElement>[] handlers = new ConsumerEventHandler[10];
        for (int i = 0; i < handlers.length; i++) {
            handlers[i] = new ConsumerEventHandler("C" + i);
        }
        WorkHandler<GoodsElement>[] seconds = new SecondConsumerEventHandler[10];
        for (int i = 0; i < seconds.length; i++) {
            seconds[i] = new SecondConsumerEventHandler("S" + i);
        }
        //设置了两个工作组，每一个工作组中对于一个event各消费一次
        EventHandlerGroup<GoodsElement> group = disruptor.handleEventsWithWorkerPool(handlers);
        group.handleEventsWithWorkerPool(seconds);
        disruptor.handleEventsWith(new ProcessService());
        disruptor.start();
        return disruptor;
    }

    @Bean
    public Disruptor<GoodsElement> serial(){
        Disruptor<GoodsElement> disruptor = getInstance();
        //设置10个串行消费的
        for (int i = 0; i < 10; i++) {
            ConsumerEventHandler handler = new ConsumerEventHandler("C" + i);
            disruptor.handleEventsWith(handler);
        }
        disruptor.start();
        return disruptor;
    }

    @Bean
    public Disruptor<GoodsElement> batch(){
        Disruptor<GoodsElement> disruptor = getInstance();
        RingBuffer<GoodsElement> ringBuffer = disruptor.getRingBuffer();
        SequenceBarrier barrier = ringBuffer.newBarrier();
        BatchEventProcessor<GoodsElement> processor = new BatchEventProcessor<>(ringBuffer, barrier, new ConsumerEventHandler("C1"));
        ringBuffer.addGatingSequences(processor.getSequence());
        TASK_EXECUTOR.execute(processor);
        disruptor.start();
        return disruptor;
    }

    @Bean
    public Disruptor<GoodsElement> factory(){
        Disruptor<GoodsElement> disruptor = getInstance();
        disruptor.handleEventsWith(new ConsumerEventFactory());
        disruptor.start();
        return disruptor;
    }
}
