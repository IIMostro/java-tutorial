package org.ilmostro.test.service.listener;

import lombok.extern.slf4j.Slf4j;
import org.ilmostro.test.domian.SimpleEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author li.bowei
 **/
@Component
@Slf4j
public class SimpleEventListener implements ApplicationListener<SimpleEvent> {

    @Override
    public void onApplicationEvent(SimpleEvent event) {
        log.info("listener application event:{}", event.getSemaphore());
    }
}
