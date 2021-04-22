package org.ilmostro.kafka.service;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
class DefaultServiceTest {

    @Autowired
    private DefaultService service;

    private static final ScheduledExecutorService SCHEDULED_EXECUTOR =
            new ScheduledThreadPoolExecutor(1,new BasicThreadFactory.
                    Builder().namingPattern("order-schedule-pool-%d").daemon(true).build());

    @Test
    public void send() throws Exception {
        SCHEDULED_EXECUTOR.scheduleAtFixedRate(new SendRunnable(service), 1, 1, TimeUnit.SECONDS);
        TimeUnit.MINUTES.sleep(10);
    }

    static class SendRunnable implements Runnable{
        private final DefaultService service;

        SendRunnable(DefaultService service) {
            this.service = service;
        }

        @Override
        public void run() {
            service.send();
        }
    }
}