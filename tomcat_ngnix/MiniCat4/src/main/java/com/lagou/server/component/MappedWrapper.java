package com.lagou.server.component;

import com.lagou.server.HttpServlet;
import com.lagou.server.Servlet;

/**
 * Servlet的包装类
 * @author wuqi
 * @date 2020-06-29 8:42
 */
public class MappedWrapper {
    //Servlet的路径映射
    private String servletUriPattern;
    //servlet类
    private HttpServlet servlet;

    public String getServletUriPattern() {
        return servletUriPattern;
    }

    public void setServletUriPattern(String servletUriPattern) {
        this.servletUriPattern = servletUriPattern;
    }

    public HttpServlet getServlet() {
        return servlet;
    }

    public void setServlet(HttpServlet servlet) {
        this.servlet = servlet;
    }
}
