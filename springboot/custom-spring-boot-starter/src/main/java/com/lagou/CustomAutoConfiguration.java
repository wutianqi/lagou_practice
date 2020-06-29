package com.lagou;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自动配置类
 * @author wuqi
 * @date 2020-06-18 8:23
 */
@Configuration
@EnableConfigurationProperties(CustomBean.class)
public class CustomAutoConfiguration {
}
