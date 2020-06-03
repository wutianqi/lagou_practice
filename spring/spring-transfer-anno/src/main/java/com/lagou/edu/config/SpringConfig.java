package com.lagou.edu.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

/**
 * Spring配置类
 * @author wuqi
 * @date 2020-06-02 22:58
 */
@Configuration
@ComponentScan(basePackages = {"com.lagou"})
//@PropertySource(value = "") //引入外部配置文件
public class SpringConfig {

    @Bean("dataSource")
    public DataSource dataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/lagou");
        dataSource.setUsername("root");
        dataSource.setPassword("root");

        return dataSource;
    }
}
