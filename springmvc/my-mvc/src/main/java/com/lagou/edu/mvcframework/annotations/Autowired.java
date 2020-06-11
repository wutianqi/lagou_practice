package com.lagou.edu.mvcframework.annotations;

import java.lang.annotation.*;

/**
 * Autowried注解
 * @author wuqi
 * @date 2020-06-10 15:46
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Autowired {
    String value() default "";
}
