package com.lagou.edu.pojo;

import org.springframework.stereotype.Component;

/**
 * Aop需要拦截的类
 * @author wuqi
 * @date 2020-06-06 15:54
 */
@Component
public class AopBean {

	public void sayHello(){
		System.out.println("hello");
	}
}
