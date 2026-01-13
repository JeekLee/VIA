package com.via.support.cache.annotation.aspect;

import com.via.support.cache.annotation.GlobalCacheEvict;
import com.via.support.cache.utils.CustomSpELParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class GlobalCacheEvictAspect {
    private final RedisTemplate<String, Object> redisTemplate;
    private final static int BATCH_SIZE = 100;

    @Around("@annotation(globalCacheEvict)")
    public Object handleGlobalCacheEvict(ProceedingJoinPoint joinPoint, GlobalCacheEvict globalCacheEvict) throws Throwable {
        Object result = joinPoint.proceed();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        if (globalCacheEvict.key() == null || globalCacheEvict.key().isEmpty()) {
            deleteWithCacheName(globalCacheEvict.cacheName());
        }

        String key = generateCacheKey(globalCacheEvict, signature.getParameterNames(), joinPoint.getArgs());
        deleteWithKey(key);

        return result;
    }

    private void deleteWithCacheName(String cacheName) {
        String pattern = cacheName + ":*";

        ScanOptions options = ScanOptions.scanOptions()
                .match(pattern)
                .count(BATCH_SIZE)
                .build();

        Cursor<String> cursor = redisTemplate.scan(options);

        List<String> keysToDelete = new ArrayList<>();
        long totalDeleted = 0;

        while (cursor.hasNext()) {
            keysToDelete.add(cursor.next());

            if (keysToDelete.size() >= BATCH_SIZE) {
                Long deleted = redisTemplate.delete(keysToDelete);
                totalDeleted += (deleted != null ? deleted : 0);
                keysToDelete.clear();
            }
        }

        if (!keysToDelete.isEmpty()) {
            Long deleted = redisTemplate.delete(keysToDelete);
            totalDeleted += (deleted != null ? deleted : 0);
        }

        log.debug("GlobalCacheEvict - Deleted {} caches for cache name: {}", totalDeleted, cacheName);
    }

    private void deleteWithKey(String key) {
        if (Boolean.TRUE.equals(redisTemplate.delete(key))) {
            log.debug("GlobalCacheEvict - Deleted cache for key: {}", key);
        }
    }

    private String generateCacheKey(GlobalCacheEvict globalCacheEvict, String[] parameterNames, Object[] args) {
        Object parsedKey = CustomSpELParser.parse(parameterNames, args, globalCacheEvict.key());
        return globalCacheEvict.cacheName() + ":" + parsedKey.toString();
    }
}
