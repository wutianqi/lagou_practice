package com.lagou.edu.demo.controller;

import com.lagou.edu.demo.service.MyService;
import com.lagou.edu.mvcframework.annotations.Autowired;
import com.lagou.edu.mvcframework.annotations.Controller;
import com.lagou.edu.mvcframework.annotations.RequestMapping;
import com.lagou.edu.mvcframework.annotations.Security;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controller
 * @author wuqi
 * @date 2020-06-10 16:05
 */
@Controller
@RequestMapping("/demo")
@Security(allowUsers = {"zhangsan", "lisi"})
public class MyController {

    @Autowired
    private MyService myService;

    @RequestMapping("/handle01")
    @Security(allowUsers = {"wangwu"})
    public String handel01(HttpServletRequest request, HttpServletResponse response, String userName) throws ServletException, IOException {
        System.out.println(userName);
        myService.test();

        return "handle01";
    }

    @RequestMapping("/handle02")
    @Security(allowUsers = {"zhaoliu"})
    public String handel02(HttpServletRequest request, HttpServletResponse response, String userName) throws ServletException, IOException {
        System.out.println(userName);
        myService.test();

        return "handle02";
    }
}
