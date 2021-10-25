package org.ilmostro.mysql.listener;

import lombok.extern.slf4j.Slf4j;
import org.ilmostro.mysql.service.impl.SpringMessagePublisherServiceImpl;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * @author li.bowei
 */
@Component
@Slf4j
public class ThrowableMessageListener implements ApplicationListener<SpringMessagePublisherServiceImpl.MessageApplicationEvent> {

    @Override
    public void onApplicationEvent(@NonNull SpringMessagePublisherServiceImpl.MessageApplicationEvent event) {
        log.info("on message user:[{}]", event.getBody());
        throw new RuntimeException();
    }
}
