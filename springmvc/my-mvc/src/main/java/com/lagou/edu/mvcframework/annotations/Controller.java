package com.lagou.edu.mvcframework.annotations;

import java.lang.annotation.*;

/**
 * Controller注解
 * @author wuqi
 * @date 2020-06-10 15:46
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {
    String value() default "";
}
