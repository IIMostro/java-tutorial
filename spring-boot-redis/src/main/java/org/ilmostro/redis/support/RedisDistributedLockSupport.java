package org.ilmostro.redis.support;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.ilmostro.redis.annotation.DistributedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author li.bowei
 */
@Aspect
@Component
public class RedisDistributedLockSupport extends AbstractExceptionHandlerSupport {

    private static final Logger logger = LoggerFactory.getLogger(RedisDistributedLockSupport.class);
    protected final RedissonClient redissonClient;
    private static final String BUSINESS_LOCK_PREFIX = "business:lock:";

    public RedisDistributedLockSupport(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Pointcut("@annotation(org.ilmostro.redis.annotation.DistributedLock)")
    public void pointcut() {

    }

    @Around("pointcut() && @annotation(distributedLock)")
    public Object lock(ProceedingJoinPoint joinPoint, DistributedLock distributedLock) throws Exception {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        String key = parse(distributedLock.key(), signature.getMethod(), args);
        String prefix = distributedLock.prefix();
        if (StringUtils.isBlank(key) && StringUtils.isBlank(prefix)) {
            throw new IllegalArgumentException("distributed lock args is null");
        }
        String lockKey = BUSINESS_LOCK_PREFIX + prefix + ":" + key;
        long lockTime = distributedLock.time();
        RLock lock;
        if (distributedLock.fair()) {
            lock = redissonClient.getFairLock(lockKey);
        } else {
            lock = redissonClient.getLock(lockKey);
        }
        boolean throwable = StringUtils.isBlank(distributedLock.exceptionHandler());
        if (lock.isLocked() && throwable) {
            throw new RuntimeException();
        } else if (lock.isLocked() && !throwable) {
            return exceptionHandler(joinPoint.getTarget(), ((MethodSignature) joinPoint.getSignature()).getMethod(),
                    args, distributedLock.exceptionHandlerClass(), distributedLock.exceptionHandler());
        }
        if (logger.isDebugEnabled()) {
            logger.debug("distributed lock start: [key:{}, object:{}, method:{}({}), args:{}, expire:{}'{}]",
                    lockKey,
                    joinPoint.getTarget().getClass().getSimpleName(),
                    signature.getName(),
                    Optional.ofNullable(args)
                            .map(Arrays::stream)
                            .orElseGet(Stream::empty)
                            .map(Object::getClass)
                            .map(Class::getSimpleName)
                            .collect(Collectors.joining(",")),
                    ArrayUtils.toString(args),
                    distributedLock.time(),
                    distributedLock.unit().name());
        }
        try {
            if (lock.tryLock(lockTime, distributedLock.unit())) {
                return joinPoint.proceed(args);
            } else {
                if (throwable) {
                    throw new RuntimeException();
                } else {
                    return exceptionHandler(joinPoint.getTarget(), ((MethodSignature) joinPoint.getSignature()).getMethod(),
                            args, distributedLock.exceptionHandlerClass(), distributedLock.exceptionHandler());
                }
            }
        } catch (Throwable e) {
            logger.error("distributed lock error: [key:{}, object:{}, method:{}({}), args:{}, expire:{}'{}]",
                    lockKey,
                    joinPoint.getTarget().getClass().getSimpleName(),
                    signature.getName(),
                    Optional.ofNullable(args)
                            .map(Arrays::stream)
                            .orElseGet(Stream::empty)
                            .map(Object::getClass)
                            .map(Class::getSimpleName)
                            .collect(Collectors.joining(",")),
                    ArrayUtils.toString(args),
                    distributedLock.time(),
                    distributedLock.unit().name());
            throw new RuntimeException();
        } finally {
            lock.unlock();
            logger.debug("distributed lock end: [key:{}]", lockKey);
        }
    }
}
