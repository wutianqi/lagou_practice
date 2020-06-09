package com.lagou.edu.hanlder;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义拦截器
 * @author wuqi
 * @date 2020-06-09 20:06
 */
public class MyHandlerInterceptor2 implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("第二个拦截器，preHandle该方法在方法执行之前（HandlerAdapter执行之前）就执行了.handlerName=" + handler.getClass().getName() + "，handler=" + handler);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("第二个拦截器，postHandle该方法在handler方法执行完毕之后执行.handlerName=" + handler.getClass().getName() + "，viewName=" + modelAndView.getViewName());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("第二个拦截器，afterCompletion该方法在dispatcher执行完请求之后执行.handlerName=" + handler.getClass().getName());
    }
}
