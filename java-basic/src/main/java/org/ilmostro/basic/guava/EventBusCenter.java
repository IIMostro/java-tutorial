package org.ilmostro.basic.guava;

import com.google.common.eventbus.EventBus;

public class EventBusCenter {


    private final static EventBus EVENT_BUS =
            new EventBus(new BaseSubscriberExceptionHandler());

    public static EventBus getInstance() {
        return EVENT_BUS;
    }

    @SafeVarargs
    public static <T extends EventBusHandler> void register(T ...obj) {
        for(T var1: obj){
            EVENT_BUS.register(var1);
        }
    }

    public static <T extends EventBusHandler> void register(T obj) {
        EVENT_BUS.register(obj);
    }

    public static <T extends EventBusHandler> void unregister(T obj) {
        EVENT_BUS.unregister(obj);
    }

    public static void post(Object obj) {
        EVENT_BUS.post(obj);
    }
}
