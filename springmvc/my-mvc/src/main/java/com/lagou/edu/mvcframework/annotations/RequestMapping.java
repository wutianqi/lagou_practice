package com.lagou.edu.mvcframework.annotations;

import java.lang.annotation.*;

/**
 * RequestMapping注解
 * @author wuqi
 * @date 2020-06-10 15:46
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
    String value() default "";
}
