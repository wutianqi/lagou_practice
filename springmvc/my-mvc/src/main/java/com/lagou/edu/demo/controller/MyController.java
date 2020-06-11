package com.lagou.edu.demo.controller;

import com.lagou.edu.demo.service.MyService;
import com.lagou.edu.mvcframework.annotations.Autowired;
import com.lagou.edu.mvcframework.annotations.Controller;
import com.lagou.edu.mvcframework.annotations.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller
 * @author wuqi
 * @date 2020-06-10 16:05
 */
@Controller
@RequestMapping("/demo")
public class MyController {

    @Autowired
    private MyService myService;

    @RequestMapping("/handle01")
    public void handel01(HttpServletRequest request, HttpServletResponse response, String name){
        System.out.println(name);
        myService.test();
    }


}
