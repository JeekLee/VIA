package com.via.support.cache.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GlobalCacheable {
    String cacheName();
    String key() default "";
    long ttl() default 3600;
}
