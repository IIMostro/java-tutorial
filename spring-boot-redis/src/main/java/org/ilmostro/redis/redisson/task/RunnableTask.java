package org.ilmostro.redis.redisson.task;

import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author li.bowei
 */
@Slf4j
public class RunnableTask implements Runnable, Serializable {

    private final Collection<Long> data;

    public RunnableTask(Collection<Long> data) {
        this.data = data;
    }

    @Override
    public void run() {
        for (Long id : data) {
            System.out.println(id);
        }
    }
}
