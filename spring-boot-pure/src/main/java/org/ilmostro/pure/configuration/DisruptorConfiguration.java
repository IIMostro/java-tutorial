package org.ilmostro.pure.configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.function.Supplier;

import com.lmax.disruptor.BatchEventProcessor;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import com.lmax.disruptor.dsl.ProducerType;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.ilmostro.pure.disruptor.ConsumerEventFactory;
import org.ilmostro.pure.disruptor.ConsumerEventHandler;
import org.ilmostro.pure.disruptor.ProcessService;
import org.ilmostro.pure.disruptor.SecondConsumerEventHandler;
import org.ilmostro.pure.disruptor.http.NettyEventHandler;
import org.ilmostro.pure.disruptor.http.NettyPromiseEvent;
import org.ilmostro.pure.disruptor.http.SimpleResponseEvent;
import org.ilmostro.pure.disruptor.http.SimpleResponseEventFactory;
import org.ilmostro.pure.disruptor.http.SimpleResponseEventHandler;
import org.ilmostro.pure.disruptor.http.VavrEventHandler;
import org.ilmostro.pure.disruptor.http.VavrPromiseEvent;
import org.ilmostro.pure.domain.ElementEventFactory;
import org.ilmostro.pure.domain.GoodsElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.annotation.Bean;

/**
 * @author li.bowei
 */
public class DisruptorConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(DisruptorConfiguration.class);

    public static final ThreadFactory DISRUPTOR_THREAD_FACTORY = new BasicThreadFactory.Builder().namingPattern("disruptor-task-%d").daemon(false).build();
    private static final Executor TASK_EXECUTOR = Executors.newFixedThreadPool(10);

//    @Bean
    public Disruptor<GoodsElement> disruptor(){
        Disruptor<GoodsElement> disruptor = getInstance(ElementEventFactory::new);
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

    public static <T> Disruptor<T> getInstance(Supplier<EventFactory<T>> supplier){
        WaitStrategy strategy = new SleepingWaitStrategy();
        int bufferSize = 1024;
        Disruptor<T> disruptor = new  Disruptor<>(supplier.get(), bufferSize, DISRUPTOR_THREAD_FACTORY, ProducerType.SINGLE, strategy);
        disruptor.setDefaultExceptionHandler(new ExceptionHandler<T>() {
            @Override
            public void handleEventException(Throwable ex, long sequence, T event) {
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

//    @Bean
    public Disruptor<GoodsElement> serialWorkPool(){
        Disruptor<GoodsElement> disruptor = getInstance(ElementEventFactory::new);
        WorkHandler<GoodsElement>[] handlers = new ConsumerEventHandler[10];
        for (int i = 0; i < handlers.length; i++) {
            handlers[i] = new ConsumerEventHandler("C" + i);
        }
        WorkHandler<GoodsElement>[] seconds = new SecondConsumerEventHandler[10];
        for (int i = 0; i < seconds.length; i++) {
            seconds[i] = new SecondConsumerEventHandler("S" + i);
        }
        // 设置了两个工作组，每一个工作组中对于一个event各消费一次
        // 只消费一次，可以理解为Kafka的分组
        EventHandlerGroup<GoodsElement> group = disruptor.handleEventsWithWorkerPool(handlers);
        group.handleEventsWithWorkerPool(seconds);
        disruptor.handleEventsWith(new ProcessService());
        disruptor.start();
        return disruptor;
    }

//    @Bean
    public Disruptor<GoodsElement> serial(){
        Disruptor<GoodsElement> disruptor = getInstance(ElementEventFactory::new);
        //设置10个串行消费的
        for (int i = 0; i < 10; i++) {
            ConsumerEventHandler handler = new ConsumerEventHandler("C" + i);
            disruptor.handleEventsWith(handler);
        }
        disruptor.start();
        return disruptor;
    }

//    @Bean
    public Disruptor<GoodsElement> batch(){
        Disruptor<GoodsElement> disruptor = getInstance(ElementEventFactory::new);
        RingBuffer<GoodsElement> ringBuffer = disruptor.getRingBuffer();
        SequenceBarrier barrier = ringBuffer.newBarrier();
        BatchEventProcessor<GoodsElement> processor = new BatchEventProcessor<>(ringBuffer, barrier, new ConsumerEventHandler("C1"));
        ringBuffer.addGatingSequences(processor.getSequence());
        TASK_EXECUTOR.execute(processor);
        disruptor.start();
        return disruptor;
    }

//    @Bean
    public Disruptor<GoodsElement> factory(){
        Disruptor<GoodsElement> disruptor = getInstance(ElementEventFactory::new);
        disruptor.handleEventsWith(new ConsumerEventFactory());
        disruptor.start();
        return disruptor;
    }

    @Bean
    public Disruptor<SimpleResponseEvent> response(){
        final Disruptor<SimpleResponseEvent> disruptor = getInstance(SimpleResponseEventFactory::new);
        for (int i = 0; i < 10; i++) {
            disruptor.handleEventsWith(new SimpleResponseEventHandler(10, i));
        }
        disruptor.start();
        return disruptor;
    }

    @Bean
    public Disruptor<NettyPromiseEvent> promise(){
        final Disruptor<NettyPromiseEvent> disruptor = getInstance(NettyPromiseEvent::new);
        for (int i = 0; i < 10; i++) {
            disruptor.handleEventsWith(new NettyEventHandler(10, i));
        }
        disruptor.start();
        return disruptor;
    }

    @Bean
    public Disruptor<VavrPromiseEvent> vavr(){
        final Disruptor<VavrPromiseEvent> disruptor = getInstance(VavrPromiseEvent::new);
        for (int i = 0; i < 10; i++) {
            disruptor.handleEventsWith(new VavrEventHandler(10, i));
        }
        disruptor.start();
        return disruptor;
    }
}
