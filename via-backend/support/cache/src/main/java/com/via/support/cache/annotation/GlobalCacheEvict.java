package com.via.support.cache.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GlobalCacheEvict {
    String cacheName();
    String key() default "";
}
