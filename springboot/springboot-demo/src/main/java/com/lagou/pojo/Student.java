package com.lagou.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Value属性注入
 * @author wuqi
 * @date 2020-06-16 8:09
 */
@Component
public class Student {
    @Value("${person.name}")
    private String name;

    public void printName(){
        System.out.println(name);
    }
}
