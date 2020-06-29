package com.lagou.server;

/**
 * 获得200以及404响应头的工具类
 * @author wuqi
 * @date 2020-06-25 22:17
 */
public class HttpProtocolUtil {
    /**
     * 返回200的相应头  HTTP/1.1 200 OK
     * @param contentLength
     * @return
     */
    public static String get200Header(long contentLength){
        return "HTTP/1.1 200 OK\n" +
                "Content-Type: text/html;charset=utf-8\n" +
                "Content-Length: " + contentLength + "\n" +
                "\r\n"; //回车+换行 因为相应头和响应体之间是有空行的
    }

    /**
     * 返回404d的相应头+内容 HTTP/1.1 404 NOT FOUND
     * @return
     */
    public static String get400Header(){
        String content = "<h1>Not Found</h1>";
        return "HTTP/1.1 404 NOT FOUND\n" +
                "Content-Type: text/html;charset=utf-8\n" +
                "Content-Length: " + content.length() + "\n" +
                "\r\n" + //回车+换行 因为相应头和响应体之间是有空行的
                content;
    }


}
