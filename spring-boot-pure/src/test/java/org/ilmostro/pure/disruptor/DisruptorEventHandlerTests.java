package org.ilmostro.pure.disruptor;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class DisruptorEventHandlerTests {

    @Test
    void test_handler_error_should_consumer(){
        final var disruptor = new Disruptor<>(SingleString::new, 1 << 16,
                new ThreadFactory() {
                    private final AtomicInteger count = new AtomicInteger();
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = Executors.defaultThreadFactory().newThread(r);
                        thread.setName(String.format("disruptor-thread-%d", count.getAndIncrement()));
                        thread.setDaemon(false);
                        return thread;
                    }
                }, ProducerType.MULTI, new BusySpinWaitStrategy());
        disruptor.handleEventsWith(new FirstEventHandler())
                        .then(new SecondEventHandler());
        disruptor.start();
        for (int i = 0; i < 100; i++) {
            final int fi = i;
            disruptor.publishEvent((event, sequence) -> event.setValue("hello world" + fi));
        }
    }

    @Getter
    @Setter
    static class SingleString{
        private String value;
    }

    static class FirstEventHandler implements EventHandler<SingleString>{

        @Override
        public void onEvent(SingleString event, long sequence, boolean endOfBatch) throws Exception {
            log.info("first event: [{}]", event.getValue());
        }
    }

    static class SecondEventHandler implements EventHandler<SingleString>{

        @Override
        public void onEvent(SingleString event, long sequence, boolean endOfBatch) throws Exception {
            try{
                log.info("second event: [{}]", event.getValue());
                throw new RuntimeException("error");
            } catch (Exception ex){
                log.error("error", ex);
            }
        }
    }

}
