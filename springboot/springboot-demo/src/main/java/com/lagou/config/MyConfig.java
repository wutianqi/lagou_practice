package com.lagou.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author wuqi
 * @date 2020-06-16 8:38
 */
@Configuration
//引入外部配置文件
@PropertySource("classpath:test.properties")
//将@ConfigurationProperties注解生效。如果类上标注了@ConfigurationProperties但是没有加@Component
//使其成为spring中的bean，那么就需要使用该注解使其生效。否则单单加了@ConfigurationProperties会报错。
@EnableConfigurationProperties({MyPropertiesConfig.class})
public class MyConfig {
}
