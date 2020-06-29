package com.lagou.server;

import java.io.IOException;

/**
 * HttpServlet
 * @author wuqi
 * @date 2020-06-26 9:27
 */
public abstract class HttpServlet implements Servlet{

    @Override
    public void init() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void service(Request request, Response response) throws IOException {
        if(request.getMethod().equalsIgnoreCase("get")){
            doGet(request, response);
        } else {
            doPost(request, response);
        }
    }

    /**
     * 处理get请求
     */
    public abstract void doGet(Request request, Response response) throws IOException;

    /**
     * 处理post请求
     */
    public abstract void doPost(Request request, Response response) throws IOException;
}
