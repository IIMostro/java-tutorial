package org.ilmostro.basic.guava;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;

@Slf4j
public class BaseSubscriberExceptionHandler implements SubscriberExceptionHandler {

    @Override
    public void handleException(@Nonnull Throwable exception, @Nonnull SubscriberExceptionContext context) {
        Object event = context.getEvent();
        EventBus eventBus = context.getEventBus();
        Object subscriber = context.getSubscriber();
        Method subscriberMethod = context.getSubscriberMethod();

        log.error("catch exception:{}", exception.getMessage());
        log.error("this event:{}, event-bus:{}, subscriber:{}, subscription-method:{}",
                event, eventBus, subscriber, subscriberMethod);
    }
}
