package org.ilmostro.redis.support;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.ilmostro.redis.annotation.DistributedRateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author li.bowei
 */
@Aspect
@Component
public class RedisDistributedRateLimiterSupport extends AbstractExceptionHandlerSupport {

    private static final String DISTRIBUTED_RATE_LIMITER_PREFIX = "business:limiter:";
    private final StringRedisTemplate redisTemplate;
    private static final Logger logger = LoggerFactory.getLogger(RedisDistributedRateLimiterSupport.class);

    public RedisDistributedRateLimiterSupport(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Pointcut("@annotation(org.ilmostro.start.annotation.DistributedRateLimiter)")
    public void pointcut() {

    }

    @Around("pointcut() && @annotation(distributedRateLimiter)")
    public Object limiter(ProceedingJoinPoint joinPoint, DistributedRateLimiter distributedRateLimiter) throws Throwable {

        String prefix = distributedRateLimiter.prefix();
        String key = distributedRateLimiter.key();
        Object[] args = joinPoint.getArgs();
        String express = parse(key, ((MethodSignature) joinPoint.getSignature()).getMethod(), args);
        if(StringUtils.isBlank(prefix) && StringUtils.isBlank(express)){
            throw new IllegalArgumentException("limiter key is null!");
        }
        String limiterCache = prefix + "-" + express;
        TimeUnit unit = distributedRateLimiter.unit();
        long time = distributedRateLimiter.time();
        Duration windows = Duration.ofSeconds(unit.toSeconds(time));
        long count = distributedRateLimiter.max();

        String handler = distributedRateLimiter.exceptionHandler();
        Class<?> handlerClass = distributedRateLimiter.exceptionHandlerClass();
        if(access(DISTRIBUTED_RATE_LIMITER_PREFIX + limiterCache, count, windows)){
            return joinPoint.proceed(args);
        }else{
            logger.warn("redis distributed limiter: [class:{} method:{}, max:{}, windows:{}'{}]",
                    joinPoint.getTarget().getClass().getSimpleName(),
                    joinPoint.getSignature().getName(),
                    count,
                    windows.getSeconds(),
                    TimeUnit.SECONDS.name());
            if(StringUtils.isBlank(handler)){
                throw new RuntimeException();
            }else{
                return exceptionHandler(joinPoint.getTarget(), ((MethodSignature) joinPoint.getSignature()).getMethod(),
                        args, handlerClass, handler);
            }
        }
    }

    protected boolean access(String key, Long maxAccessCount, Duration windows){
        Long count = Optional.of(key)
                .map(redisTemplate::boundZSetOps)
                .map(BoundZSetOperations::zCard)
                .orElse(0L);
        if(count >= maxAccessCount){
            return false;
        }
        increment(key, windows);
        return true;
    }

    private void increment(String key, Duration duration) {
        long current = System.currentTimeMillis();
        long max = current - duration.toMillis();
        SessionCallback<Object> callback = new SessionCallback<Object>() {
            @Override
            public Object execute(@NonNull RedisOperations operations) throws DataAccessException {
                operations.multi();
                //noinspection unchecked
                BoundZSetOperations<String, String> zSetOperations = operations.boundZSetOps(key);
                zSetOperations.removeRangeByScore(0, max);
                zSetOperations.add(key + "-" + UUID.randomUUID().toString(), current);
                zSetOperations.expire(duration);
                return operations.exec();
            }
        };
        redisTemplate.execute(callback);
    }

}
