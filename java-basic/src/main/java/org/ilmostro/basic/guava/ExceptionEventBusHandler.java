package org.ilmostro.basic.guava;

import com.google.common.eventbus.Subscribe;

public class ExceptionEventBusHandler implements EventBusHandler{
    @Override
    @Subscribe
    public void observer(Object object) {
        throw new RuntimeException("this is error event bus handler!");
    }
}
