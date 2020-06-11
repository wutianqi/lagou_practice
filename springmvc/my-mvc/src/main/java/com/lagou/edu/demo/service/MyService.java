package com.lagou.edu.demo.service;

import com.lagou.edu.mvcframework.annotations.Service;

/**
 * 我的Service
 * @author wuqi
 * @date 2020-06-10 20:08
 */
@Service
public class MyService {
    public void test(){
        System.out.println("调用service方法...");
    }
}
