package com.lagou;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lagou.dao")
public class SpringbootDemoDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootDemoDataApplication.class, args);
    }

}
