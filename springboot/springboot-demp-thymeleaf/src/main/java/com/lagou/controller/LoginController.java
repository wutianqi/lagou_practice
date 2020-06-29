package com.lagou.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Calendar;

/**
 * @author wuqi
 * @date 2020-06-20 22:57
 */
@Controller
public class LoginController {

    @RequestMapping("/toLoginPage")
    public String toLoginPage(Model model){
        int year = Calendar.getInstance().get(Calendar.YEAR);
        model.addAttribute("currentYear", year);

        return "login";
    }
}
