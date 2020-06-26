package com.lagou.server;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * MiniCat的启动类
 * MiniCat的功能：
 * 接收来自浏览器的请求，解析Http协议，封装成Request，然后加载静态资源、Servlet，然后封装成Response对象，返回响应。
 * @author wuqi
 * @date 2020-06-25 22:03
 */
public class BootStrap {


    /**
     * MiniCat1.0版本：访问http://localhost:8080 向浏览器中输出Hello MiniCat字符串
     * 通过这个例子可以看出Http是应用层的协议
     */
//    public static void start() throws IOException {
//        //创建ServerSocket
//        ServerSocket serverSocket = new ServerSocket(8080);
//        System.out.println("=========================>MiniCat1.0已经启动...");
//
//        while (true){
//            //接收请求
//            Socket socket = serverSocket.accept();
//            //输出流
//            OutputStream outputStream = socket.getOutputStream();
//            String str = "Hello MiniCat";
//            String content = HttpProtocolUtil.get200Header(str.length()) + str;
//            outputStream.write(content.getBytes("UTF-8"));
//            outputStream.close();
//        }
//    }

    /**
     * MiniCat2.0版本：将请求封装成Request对象，解析Http协议，加载静态资源，然后封装成Response对象，向浏览器中输出内容。
     */
//    public static void start() throws IOException {
//        ServerSocket serverSocket = new ServerSocket(8080);
//        System.out.println("=========================>MiniCat2.0已经启动...");
//
//        while (true){
//            Socket socket = serverSocket.accept();
//            InputStream inputStream = socket.getInputStream();
//            //封装成request对象
//            Request request = new Request(inputStream);
//            //封装成response对象
//            Response response = new Response(socket.getOutputStream());
//
//            //输出静态资源
//            response.outPutHtml(request.getUri());
//        }
//    }

    /**
     * MiniCat3.0版本，实现动态资源的访问
     */
//    public static void start() throws Exception {
//        ServerSocket serverSocket = new ServerSocket(8080);
//
//        //加载web.xml中的Servlet
//        Map<String, HttpServlet> httpServletMap = loadServlet();
//        System.out.println("=========================>MiniCat3.0已加载所有HttpServlet...");
//        System.out.println("=========================>MiniCat3.0已经启动...");
//
//        while (true){
//            Socket socket = serverSocket.accept();
//            InputStream inputStream = socket.getInputStream();
//            //封装成request对象
//            Request request = new Request(inputStream);
//            //封装成response对象
//            Response response = new Response(socket.getOutputStream());
//
//            //判断是servletMap中是否有HttpServlet与之相对应，有的话调用Servlet，没有的话则调用静态资源
//            if(httpServletMap.containsKey(request.getUri())){
//                HttpServlet httpServlet = httpServletMap.get(request.getUri());
//                httpServlet.service(request, response);
//            } else {
//                //输出静态资源
//                response.outPutHtml(request.getUri());
//            }
//            socket.close();
//        }
//    }

    /**
     * MiniCat3.0改造，使用多线程（不使用线程池）
     */
//    public static void start() throws Exception {
//        ServerSocket serverSocket = new ServerSocket(8080);
//
//        //加载web.xml中的Servlet
//        Map<String, HttpServlet> httpServletMap = loadServlet();
//        System.out.println("=========================>MiniCat3.0已加载所有HttpServlet...");
//        System.out.println("=========================>MiniCat3.0已经启动...");
//
//        while (true){
//            Socket socket = serverSocket.accept();
//
//            //使用线程
//            RequestProcessor requestProcessor = new RequestProcessor(socket, httpServletMap);
//            //开启线程处理请求
//            requestProcessor.start();
//        }
//    }

    /**
     * MiniCat3.0改造，使用多线程处理请求（多线程）
     */
    public static void start() throws Exception {
        ServerSocket serverSocket = new ServerSocket(8080);

        //加载web.xml中的Servlet
        Map<String, HttpServlet> httpServletMap = loadServlet();
        System.out.println("=========================>MiniCat3.0已加载所有HttpServlet...");
        System.out.println("=========================>MiniCat3.0已经启动...");

        //创建线程池
        int coreSize = 10;
        int maxPoolSize = 20;
        long keepAliveTime = 100l;
        TimeUnit timeUnit = TimeUnit.MICROSECONDS;
        BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(20);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        RejectedExecutionHandler abortPolicy = new ThreadPoolExecutor.AbortPolicy();

        //创建线程池
        ThreadPoolExecutor executor = new ThreadPoolExecutor(coreSize, maxPoolSize, keepAliveTime, timeUnit, blockingQueue, threadFactory, abortPolicy);

        while (true){
            Socket socket = serverSocket.accept();

            //使用线程
            RequestProcessor requestProcessor = new RequestProcessor(socket, httpServletMap);
            //使用线程池执行任务
            executor.execute(requestProcessor);
        }
    }

    private static Map<String, HttpServlet> loadServlet() throws Exception {
        //存储urlPattern和HttpServlet之间映射的Map
        Map<String, HttpServlet> httpServletMap = new HashMap<>();

        //读取web.xml配置文件
        InputStream inputStream = BootStrap.class.getClassLoader().getResourceAsStream("web.xml");

        //获取所有的<servlet>标签
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        Element rootElement = document.getRootElement();
        List<Element> elements = rootElement.selectNodes("//servlet");
        for(Element element : elements){
            //解析servlet标签
            //<servlet-name>helloServlet</servlet-name>
            Element servletNameEle = (Element) element.selectSingleNode("servlet-name");
            String servletName = servletNameEle.getStringValue();
            //<servlet-class>com.lagou.server.MyHttpServlet</servlet-class>
            Element servletClassEle = (Element) element.selectSingleNode("servlet-class");
            String servletClass = servletClassEle.getStringValue();

            //获取对应的servlet-mapping标签
            Element servletMappingEle = (Element)rootElement.selectSingleNode("/web-app/servlet-mapping[servlet-name='" + servletName + "']");
            Element urlPatternEle = (Element) servletMappingEle.selectSingleNode("url-pattern");
            String urlPattern = urlPatternEle.getStringValue();
            System.out.println("===========================>获取到urlPattern=" + urlPattern + "的Servlet");

            //创建HttpServlet
            HttpServlet httpServlet = (HttpServlet)Class.forName(servletClass).getDeclaredConstructor().newInstance();

            //存储urlPattern与HttpServlet之间的映射
            httpServletMap.put(urlPattern, httpServlet);
        }

        return httpServletMap;
    }


    public static void main(String[] args) {
        try {
            start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
