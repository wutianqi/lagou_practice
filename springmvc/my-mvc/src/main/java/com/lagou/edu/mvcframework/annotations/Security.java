package com.lagou.edu.mvcframework.annotations;

import java.lang.annotation.*;

/**
 * 权限拦截注解
 * @author wuqi
 * @date 2020-06-13 20:21
 */
@Documented
@Target(value = {ElementType.TYPE, ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Security {
    /**
     * 有权限访问handler的用户名
     */
    String[] allowUsers() default {};
}
