package org.ilmostro.redis.controller;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author li.bowei
 */
@RequestMapping("/lock")
@RestController
@Slf4j
public class LockV1Controller {

    private final RLock global;
    public final static LongAdder success = new LongAdder();
    public final static LongAdder failure = new LongAdder();
    public final static LongAdder error = new LongAdder();

    public LockV1Controller(RedissonClient redisson) {
        this.global = redisson.getLock("redisson:global");
    }

    @GetMapping("/timeout")
    public String tryLock() {
        try {
            if (global.tryLock(100, TimeUnit.MILLISECONDS)) {
                success.increment();
                TimeUnit.SECONDS.sleep(1);
            } else {
                failure.increment();
            }
        } catch (InterruptedException e) {
            error.increment();
        }
        return "";
    }
}
