package com.custom.impot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 给springboot-demo 使用@Impot{ImportSelector}进行导入
 * @author wuqi
 * @date 2020-06-19 8:26
 */
@Configuration
public class NotAutoConfig2 {

    @Bean("myBean2")
    public MyBean myBean(){
        MyBean myBean = new MyBean();
        myBean.setId(1002);
        myBean.setName("myBean2");
        return myBean;
    }
}
