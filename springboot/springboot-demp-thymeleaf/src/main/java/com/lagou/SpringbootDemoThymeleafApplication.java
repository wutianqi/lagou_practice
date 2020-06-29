package com.lagou;

import com.lagou.resolver.MyLocalResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;

@SpringBootApplication
public class SpringbootDemoThymeleafApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootDemoThymeleafApplication.class, args);
    }


    @Bean
    public LocaleResolver localResolver(){
        return new MyLocalResolver();
    }
}
