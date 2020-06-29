package com.lagou.server;

import com.lagou.server.component.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;

/**
 * 请求处理线程
 * @author wuqi
 * @date 2020-06-26 10:04
 */
public class RequestProcessor extends Thread {
    private Socket socket;
    private Service service;

    public RequestProcessor(Socket socket, Service service){
        this.socket = socket;
        this.service = service;
    }


    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            //封装成request对象
            Request request = new Request(inputStream);
            //封装成response对象
            Response response = new Response(socket.getOutputStream());

            //在Mapper中寻找匹配的Servlet
            Mapper mapper = service.getMapper();
            List<MappedHost> mappedHosts = mapper.getMappedHosts();

            boolean find = false;
            for(MappedHost mappedHost : mappedHosts){
                //寻找host
                if(mappedHost.getName().equals(request.getHost())){
                    //寻找context
                    List<MappedContext> mappedContexts = mappedHost.getMappedContexts();
                    for(MappedContext mappedContext : mappedContexts){
                        if(request.getUri().startsWith(mappedContext.getName(), 1)){
                            //寻找wrapper
                            List<MappedWrapper> mappedWrappers = mappedContext.getMappedWrappers();
                            for(MappedWrapper mappedWrapper : mappedWrappers){
                                String servletUri = "/" + mappedContext.getName() + mappedWrapper.getServletUriPattern();
                                if(request.getUri().equals(servletUri)){
                                    find = true;
                                    //找到wrapper
                                    HttpServlet servlet = mappedWrapper.getServlet();
                                    servlet.service(request, response);
                                }
                            }
                        }
                    }
                }
            }

            if(!find){
                //找不到则显示404
                response.output(HttpProtocolUtil.get400Header());
            }

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
