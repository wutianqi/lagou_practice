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
public class MyHandlerInterceptor implements HandlerInterceptor {
    /**
     * 往往在这里完成权限校验工作
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("第一个拦截器，preHandle该方法在方法执行之前（HandlerAdapter执行之前）就执行了.handlerName=" + handler.getClass().getName() + "，handler=" + handler);
        return true;
    }

    /**
     * 会在handler⽅法业务逻辑执⾏之后尚未跳转⻚⾯时执⾏.如果handler抛出异常，那么不会执行
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("第一个拦截器，postHandle该方法在handler方法执行完毕之后执行.handlerName=" + handler.getClass().getName() + "，viewName=" + modelAndView.getViewName());
    }

    /**
     * ⻚⾯已经跳转渲染完毕之后执⾏
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("第一个拦截器，afterCompletion该方法在dispatcher执行完请求之后执行.handlerName=" + handler.getClass().getName());
    }
}
