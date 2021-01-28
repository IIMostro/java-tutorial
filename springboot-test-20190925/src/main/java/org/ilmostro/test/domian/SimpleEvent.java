package org.ilmostro.test.domian;

import org.springframework.context.ApplicationEvent;

/**
 * @author li.bowei
 **/
public class SimpleEvent extends ApplicationEvent {

    private final String semaphore;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public SimpleEvent(Object source, String semaphore) {
        super(source);
        this.semaphore = semaphore;
    }

    public String getSemaphore() {
        return semaphore;
    }
}
