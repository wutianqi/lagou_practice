package com.lagou.edu.factory;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * 使用FactoryBean创建对象
 * @author wuqi
 * @date 2020-06-03 6:39
 */
@Component("user")
public class MyFactoryBean implements FactoryBean<User> {

    @Override
    public User getObject() throws Exception {
        User user = new User();
        user.setUsername("zs");
        user.setPassword("123");
        return user;
    }

    @Override
    public Class<?> getObjectType() {
        return User.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}

class User{
   private String username;
   private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
