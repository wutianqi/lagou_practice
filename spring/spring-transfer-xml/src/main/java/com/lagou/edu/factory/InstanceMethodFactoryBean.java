package com.lagou.edu.factory;

/**
 * 实例化方法创建Bean
 * @author wuqi
 * @date 2020-06-02 18:52
 */
public class InstanceMethodFactoryBean {
    public User getUser(){
        User user = new User();
        user.setUsername("zhangsan");
        user.setPassword("ls");
        return user;
    }
}
