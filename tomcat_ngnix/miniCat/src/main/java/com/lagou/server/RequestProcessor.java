package com.lagou.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Map;

/**
 * 请求处理线程
 * @author wuqi
 * @date 2020-06-26 10:04
 */
public class RequestProcessor extends Thread {
    private Socket socket;
    private Map<String, HttpServlet> httpServletMap;

    public RequestProcessor(Socket socket, Map<String, HttpServlet> httpServletMap){
        this.socket = socket;
        this.httpServletMap = httpServletMap;
    }


    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            //封装成request对象
            Request request = new Request(inputStream);
            //封装成response对象
            Response response = new Response(socket.getOutputStream());

            //判断是servletMap中是否有HttpServlet与之相对应，有的话调用Servlet，没有的话则调用静态资源
            if(httpServletMap.containsKey(request.getUri())){
                HttpServlet httpServlet = httpServletMap.get(request.getUri());
                httpServlet.service(request, response);
            } else {
                //输出静态资源
                response.outPutHtml(request.getUri());
            }
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
