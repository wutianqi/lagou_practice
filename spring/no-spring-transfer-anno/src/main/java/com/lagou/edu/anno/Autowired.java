package com.lagou.edu.anno;

import java.lang.annotation.*;

/**
 * Autowired注解
 * @author wuqi
 * @date 2020-06-06 21:22
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {
}
