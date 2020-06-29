package com.lagou.pojo;

import org.springframework.stereotype.Component;

/**
 * 这个类用于验证，@EnableAutoConfiguration注解只是用来加载自动配置的类，以及主启动类的。
 * 并不会进行包扫描。内部的@AutoConfigurationPackage只是用来存储自动配置包名称的。
 * @author wuqi
 * @date 2020-06-20 9:01
 */
public class Hero {
    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Hero{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
