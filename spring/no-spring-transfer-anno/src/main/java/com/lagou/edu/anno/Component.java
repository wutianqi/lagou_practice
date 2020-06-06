package com.lagou.edu.anno;

import java.lang.annotation.*;

/**
 * Component注解
 * @author wuqi
 * @date 2020-06-06 21:22
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {
    String value() default "";
}
