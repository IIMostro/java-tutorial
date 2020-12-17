package org.ilmostro.basic.guava;

import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FirstEventBusHandler implements EventBusHandler{

    @Subscribe
    @Override
    public void observer(Object object) {
        log.info("this is first event bus handler, receive:{}", object);
    }
}
