package com.via.support.cache.annotation.aspect;

import com.via.support.cache.annotation.GlobalCacheable;
import com.via.support.cache.utils.CustomSpELParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class GlobalCacheableAspect {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper mapper;

    @Around("@annotation(globalCacheable)")
    public Object handleGlobalCacheable(ProceedingJoinPoint joinPoint, GlobalCacheable globalCacheable) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String key = generateCacheKey(globalCacheable, signature.getParameterNames(), joinPoint.getArgs());

        Object cachedValue = redisTemplate.opsForValue().get(key);

        if (cachedValue != null) {
            log.debug("GlobalCacheable - Cache hit for key: {}", key);
            return convertToReturnType(cachedValue, signature);
        }

        log.debug("GlobalCacheable - Cache miss for key: {}", key);
        Object result = joinPoint.proceed();

        redisTemplate.opsForValue().set(key, result, globalCacheable.ttl(), TimeUnit.SECONDS);
        log.debug("GlobalCacheable - Cached result for key: {} with TTL: {}s", key, globalCacheable.ttl());

        return result;
    }

    private String generateCacheKey(GlobalCacheable globalCacheable, String[] parameterNames, Object[] args) {
        String cacheName = globalCacheable.cacheName();
        String key = globalCacheable.key();
        if (key == null || key.isEmpty()) return cacheName;

        Object parsedKey = CustomSpELParser.parse(parameterNames, args, globalCacheable.key());
        return cacheName + ":" + parsedKey.toString();
    }

    private Object convertToReturnType(Object cachedValue, MethodSignature signature) {
        if (signature.getReturnType().isInstance(cachedValue)) return cachedValue;
        return mapper.convertValue(cachedValue, signature.getReturnType());
    }
}
