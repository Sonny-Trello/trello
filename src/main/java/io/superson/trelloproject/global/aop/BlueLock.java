package io.superson.trelloproject.global.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BlueLock {

    String value() default "lock";

    long waitTime() default 5000;

    long leaseTime() default 500;

    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;
}
