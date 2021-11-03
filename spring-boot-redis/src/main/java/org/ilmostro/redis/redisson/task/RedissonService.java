package org.ilmostro.redis.redisson.task;

import org.redisson.api.RScheduledExecutorService;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @author li.bowei
 */
@Service
public class RedissonService {

    private final RedissonClient redisson;
    private final Collection<Long> collection = new CopyOnWriteArrayList<>();

    public RedissonService(RedissonClient redisson) {
        this.redisson = redisson;
    }

    public void schedule(){
        RScheduledExecutorService executor = redisson.getExecutorService("executor");
        executor.scheduleAtFixedRate(new RunnableTask(collection), 1, 1, TimeUnit.SECONDS);
    }

    public void setCollection(Long data) {
        collection.add(data);
    }
}
