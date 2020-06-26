package com.lagou.server;

import java.io.IOException;

/**
 * 自定义HttpServlet
 * @author wuqi
 * @date 2020-06-26 9:31
 */
public class MyHttpServlet extends HttpServlet{

    @Override
    public void doGet(Request request, Response response) throws IOException {
        //睡眠，模拟很长时间的业务逻辑执行
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //输出的内容
        String content = "<h1>Hello Servlet</h1>";
        //输出
        response.getOutputStream().write((HttpProtocolUtil.get200Header(content.length()) + content).getBytes());
    }

    @Override
    public void doPost(Request request, Response response) throws IOException {
        doGet(request, response);
    }
}
