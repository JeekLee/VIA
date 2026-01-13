package com.via.support.concurrency.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GlobalLock {
    String lockName();                              // Name of lock
    String key() default "";                        // Key of lock
    TimeUnit timeUnit() default TimeUnit.SECONDS;   // Default timeunit: second
    long waitTime() default 5L;                     // Default timeout of lock request
    long leaseTime() default 5L;                    // Default timeout of lock holding
}
