package com.lagou.edu.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 权限拦截器
 * @author wuqi
 * @date 2020-06-14 0:40
 */
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String requestURI = request.getRequestURI();
        if(requestURI.equals("/login")){
            //不拦截登录
            return true;
        }

        HttpSession session = request.getSession();
        Object userInfo = session.getAttribute("userInfo");
        if(userInfo != null){
            //已登录，则放行
            return true;
        }

        //未登录，跳到登录页
        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
        return false;
    }
}
