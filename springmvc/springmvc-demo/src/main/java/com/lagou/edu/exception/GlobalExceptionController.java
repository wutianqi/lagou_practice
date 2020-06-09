package com.lagou.edu.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 全局异常处理器
 * @author wuqi
 * @date 2020-06-09 20:46
 */
@ControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception e, HttpServletResponse response) throws IOException {
        e.printStackTrace();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");

        response.setContentType("text/plain");
        response.getWriter().write("zzzz");

        response.flushBuffer();

        return null;
    }

}
