package com.lagou.edu.factory;

/**
 * 静态方法生产Bean
 * @author wuqi
 * @date 2020-06-02 18:48
 */
public class StaticMethodFactoryBean {

    public static User getUser(){
        User user = new User();
        user.setUsername("zs");
        user.setPassword("123456");
        return user;
    }

}

class User{
    String username;
    String password;

    public void destory(){
        System.out.println("user被销毁了...");
    }

    public void inti(){
        System.out.println("user初始化...");
    }

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
}
