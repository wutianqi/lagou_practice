package com.lagou.edu.anno;

import java.lang.annotation.*;

/**
 * Transactional注解
 * @author wuqi
 * @date 2020-06-06 21:22
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Transactional {
}
