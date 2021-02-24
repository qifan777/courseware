package com.jarcheng.mbg.annotation;

import java.lang.annotation.*;

/**
 * <p>
 *     aop切入注解
 * </p>
 * @author Lazy
 * @create 2021-01-22 17:24
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestLog {
    String value() default "";
}
