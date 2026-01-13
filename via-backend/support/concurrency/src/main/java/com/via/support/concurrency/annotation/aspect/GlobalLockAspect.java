package com.via.support.concurrency.annotation.aspect;

import com.via.core.error.ExceptionCreator;
import com.via.support.concurrency.annotation.GlobalLock;
import com.via.support.concurrency.utils.CustomSpELParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static com.via.support.concurrency.exception.GlobalLockException.LOCK_REQUEST_FAILED;
import static com.via.support.concurrency.exception.GlobalLockException.LOCK_UNAVAILABLE;

@Slf4j
@Order(Ordered.LOWEST_PRECEDENCE - 1)
@Aspect
@Component
@RequiredArgsConstructor
public class GlobalLockAspect {
    private final RedissonClient redissonClient;
    private final TransactionForAspect transactionForAspect;

    @Around("@annotation(globalLock)")
    public Object lock(ProceedingJoinPoint joinPoint, GlobalLock globalLock) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String key = generateLockKey(globalLock, signature.getParameterNames(), joinPoint.getArgs());

        RLock rLock = redissonClient.getLock(key);
        String rLockName = rLock.getName();
        try {
            if (!rLock.tryLock(globalLock.waitTime(), globalLock.leaseTime(), globalLock.timeUnit())) {
                throw ExceptionCreator.create(LOCK_UNAVAILABLE, "key: " + key);
            }
            return transactionForAspect.proceed(joinPoint);
        } catch (InterruptedException e) {
            throw ExceptionCreator.create(LOCK_REQUEST_FAILED, "key: " + key);
        } finally {
            try {
                rLock.unlock();
            } catch (IllegalMonitorStateException e) {
                log.info("Redisson Lock [{}] Already Unlocked", rLockName);
            }
        }
    }

    private String generateLockKey(GlobalLock globalLock, String[] parameterNames, Object[] args) {
        String lockName = globalLock.lockName();
        String key = globalLock.key();
        if (key == null || key.isEmpty()) return lockName;

        Object parsedKey = CustomSpELParser.parse(parameterNames, args, globalLock.key());

        return lockName + ":" + parsedKey.toString();
    }
}
