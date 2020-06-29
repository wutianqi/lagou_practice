package com.custom.impot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 给springboot-demo 用@Import引入的配置类
 * @author wuqi
 * @date 2020-06-19 8:16
 */
@Configuration
public class NotAutoConfig {
    @Bean("myBean1")
    public MyBean myBean(){
        MyBean myBean = new MyBean();
        myBean.setId(1001);
        myBean.setName("myBean1");
        return myBean;
    }
}
