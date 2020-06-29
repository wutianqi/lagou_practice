package com.lagou.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 随机数属性引入
 * @author wuqi
 * @date 2020-06-16 8:58
 */
@Component
public class Tom {
    @Value("${tom.age}")
    private String age;

    @Value("${tom.description}")
    private String description;

    public void printAge(){
        System.out.println(age + "->" + description);
    }
}
