package com.lagou.edu.controller;

import com.lagou.edu.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录
 * @author wuqi
 * @date 2020-06-14 0:37
 */
@Controller
@RequestMapping("/")
public class LoginController {

    /**
     * 登录
     * @param user
     * @return
     */
    @RequestMapping("/login")
    public String login(User user, HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        ModelAndView modelAndView = new ModelAndView();
        Object userInfo = session.getAttribute("userInfo");
        if(userInfo != null){
            //登录了就直接跳转到简历列表页面
            return "redirect:/resume/list";
        }

        //没登陆，则登录。
        String username = user.getUsername();
        String password = user.getPassword();
        if("admin".equals(username) && "admin".equals(password)){
            //登录成功，将用户信息存储到session中
            request.getSession().setAttribute("userInfo", user);
            return "redirect:/resume/list";
        }

        //登录失败
        return "error";
    }
}
