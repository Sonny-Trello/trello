package io.superson.trelloproject.global.aop;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Slf4j(topic = "RedisLock")
@Aspect
@Component
@AllArgsConstructor
public class RedisLock {

    private final RedissonClient redissonClient;

    @Around("@annotation(blueLock)")
    public Object applyLock(ProceedingJoinPoint joinPoint, BlueLock blueLock) throws Throwable {
        RLock lock = redissonClient.getFairLock(blueLock.value());
        if (lock.tryLock(blueLock.waitTime(), blueLock.leaseTime(), blueLock.timeUnit())) {
            try {
                log.info("Locking");
                return joinPoint.proceed();
            } finally {
                lock.unlock();
                log.info("Unlocking");
            }
        } else {
            throw new RuntimeException("Failed to acquire lock");
        }
    }
}
