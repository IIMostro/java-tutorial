package org.ilmostro.redis.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author li.bowei
 */
@RequestMapping("/lock")
@RestController
@Slf4j
public class LockController {

    private final RLock writeLock;
    private final RLock readLock;
    private final RLock global;
    private final StringRedisTemplate redis;
    private static final LongAdder hit = new LongAdder();
    private static final LongAdder miss = new LongAdder();
    private static final LongAdder write = new LongAdder();
    private static final Map<String, Long> competition = new ConcurrentHashMap<>();

    public LockController(RedissonClient redisson, StringRedisTemplate stringRedisTemplate) {
        this.redis = stringRedisTemplate;
        RReadWriteLock readWriteLock = redisson.getReadWriteLock("redisson:lock");
        this.global = redisson.getLock("redisson:global");
        this.writeLock = readWriteLock.writeLock();
        this.readLock = readWriteLock.readLock();
    }

    @GetMapping("/normal")
    public String normal(){
        return get();
    }

    public String get(){
        String value = redis.opsForValue().get("redis:lock:value");
        if (StringUtils.isNotBlank(value) || "NULL".equals(value)) {
            hit.increment();
            return value;
        }
        miss.increment();
        write.increment();
        value = String.valueOf(System.currentTimeMillis());
        redis.opsForValue().set("redis:lock:value", value, 100, TimeUnit.MILLISECONDS);
        return value;
    }

    @GetMapping("/try")
    public String tryLock(){
        String value = redis.opsForValue().get("redis:lock:value");
        if (StringUtils.isNotBlank(value) || "NULL".equals(value)) {
            hit.increment();
            return value;
        }
        try {
            if (global.tryLock(100, TimeUnit.MILLISECONDS)) {
                value = get();
                competition.merge(value, 1L, Long::sum);
            }
            return value;
        } catch (InterruptedException e) {
            return "NULL";
        } finally {
            if(global.isHeldByCurrentThread() && global.isLocked()) global.unlock();
        }
    }

    @GetMapping("/statistic")
    public void statistic(){
        log.info("write:{}, hit:{}, miss{}", write, hit, miss);
        log.info("success:{}, error:{}, failure: {}", LockV1Controller.success, LockV1Controller.error, LockV1Controller.failure);
        competition.forEach((k, v) -> log.info("competition value:{}, count:{}", k, v));
    }

    @GetMapping("/clean")
    public void clean(){
        write.reset();
        hit.reset();
        miss.reset();
        competition.clear();
        LockV1Controller.error.reset();
        LockV1Controller.failure.reset();
    }

    @GetMapping("/global")
    public String global() throws InterruptedException {
        String value = redis.opsForValue().get("redis:lock:value");
        if (StringUtils.isNotBlank(value) || "NULL".equals(value)) {
            hit.increment();
            return value;
        }
        global.lock(100, TimeUnit.MILLISECONDS);
        value = get();
        if(global.isLocked() && global.isHeldByCurrentThread()) global.unlock();
        return value;
    }

    @GetMapping("/write")
    public String read() {
        readLock.lock(100, TimeUnit.MILLISECONDS);
        String value = redis.opsForValue().get("redis:lock:value");
        if (StringUtils.isNotBlank(value) || "NULL".equals(value)) {
            if (readLock.isLocked() && readLock.isHeldByCurrentThread()) readLock.unlock();
            hit.increment();
            return value;
        }
        writeLock.lock();
        value = redis.opsForValue().get("redis:lock:value");
        if (StringUtils.isNotBlank(value) || "NULL".equals(value)) {
            if (readLock.isLocked() && readLock.isHeldByCurrentThread()) readLock.unlock();
            if (writeLock.isLocked() && writeLock.isHeldByCurrentThread()) writeLock.unlock();
            hit.increment();
            return value;
        }
        miss.increment();
        write.increment();
        value = String.valueOf(System.currentTimeMillis());
        redis.opsForValue().set("redis:lock:value", value, 100, TimeUnit.MILLISECONDS);
        if (writeLock.isLocked() && writeLock.isHeldByCurrentThread()) writeLock.unlock();
        if (readLock.isLocked() && writeLock.isHeldByCurrentThread()) readLock.unlock();
        return value;
    }
}
