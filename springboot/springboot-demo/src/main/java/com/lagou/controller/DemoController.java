package com.lagou.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuqi
 * @date 2020-06-15 8:58
 */
@RestController
public class DemoController {

    @RequestMapping("/demo")
    public String demo(){
        return "你好  hello springboot...";
    }
}
