package org.ilmostro.pure.disruptor;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.dsl.Disruptor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.bridge.Message;
import org.ilmostro.pure.configuration.DisruptorConfiguration;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

@Slf4j
public class DisruptorV1Tests {

    @Data
    private static class Event implements EventFactory<Event> {
        private String name;
        private int age;

        @Override
        public Event newInstance() {
            return new Event();
        }
    }

    private record MessageEventHandler(String name) implements EventHandler<Event> {

        @Override
            public void onEvent(Event event, long sequence, boolean endOfBatch) throws Exception {
                log.info("message event handler:[{}] -> event[{}]", name, event);
            }
        }


    @Test
    void simple_consumer() throws Exception{
        Disruptor<Event> disruptor = DisruptorConfiguration.getInstance(Event::new);
        MessageEventHandler handler1 = new MessageEventHandler("handler1");
        MessageEventHandler handler2 = new MessageEventHandler("handler2");
        disruptor.handleEventsWith(handler1, handler2);
        disruptor.start();
        for (int i = 0; i < 10; i++) {
            final int fi = i;
            disruptor.publishEvent(((event, sequence) -> {
                event.setName("name" + fi);
                event.setAge(fi);
            }));
        }
        TimeUnit.SECONDS.sleep(2);
    }

    @Test
    void disruptor_and_handler_should_work() throws Exception{
        Disruptor<Event> disruptor = DisruptorConfiguration.getInstance(Event::new);
        MessageEventHandler handler1 = new MessageEventHandler("handler1");
        MessageEventHandler handler2 = new MessageEventHandler("handler2");
        disruptor.handleEventsWith(handler1, handler2).then(new MessageEventHandler("handler3"));
        disruptor.start();
        for (int i = 0; i < 10; i++) {
            final int fi = i;
            disruptor.publishEvent(((event, sequence) -> {
                event.setName("name" + fi);
                event.setAge(fi);
            }));
        }
        TimeUnit.SECONDS.sleep(2);
        disruptor.shutdown();
    }

    @Test
    void disruptor_rhombus_handler_should_work() throws Exception{
        Disruptor<Event> disruptor = DisruptorConfiguration.getInstance(Event::new);
        // 先消费handler1, handler2，然后再消费handler3
        MessageEventHandler handler1 = new MessageEventHandler("handler1");
        MessageEventHandler handler2 = new MessageEventHandler("handler2");
        MessageEventHandler handler3 = new MessageEventHandler("handler3");
        disruptor.handleEventsWith(handler1, handler2).then(handler3);
        disruptor.start();
        for (int i = 0; i < 10; i++) {
            final int fi = i;
            disruptor.publishEvent(((event, sequence) -> {
                event.setName("name" + fi);
                event.setAge(fi);
            }));
        }
        TimeUnit.SECONDS.sleep(2);
        disruptor.shutdown();
    }
}
