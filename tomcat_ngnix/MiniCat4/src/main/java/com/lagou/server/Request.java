package com.lagou.server;

import java.io.IOException;
import java.io.InputStream;

/**
 * 请求对象，解析Http协议
 * @author wuqi
 * @date 2020-06-25 22:15
 */
public class Request {
    /**
     * 请求方法
     */
    private String method;
    /**
     * 请求uri
     */
    private String uri;
    /**
     * 输入流
     */
    private InputStream inputStream;
    /**
     * 注解名
     */
    private String host;


    public Request(){

    }

    public Request(InputStream inputStream) throws IOException {
        this.inputStream = inputStream;
        int available = 0;
        while (available == 0){
            available = inputStream.available();
        }
        byte[] buffer = new byte[available];
        inputStream.read(buffer);
        String requestContent = new String(buffer);

        //设置method和uri
        //以空行进行分割
        String[] split = requestContent.split("\n");
        String requestHeader = split[0]; //GET / HTTP/1.1
        //以空格进行分割
        String[] requestHeaderStrs = requestHeader.split(" ");
        this.method = requestHeaderStrs[0];
        this.uri = requestHeaderStrs[1];

        //设置host
        for(String line : split){
            String[] lineElements = line.split(":");
            if(lineElements[0].equalsIgnoreCase("Host")){
                this.host = lineElements[1].trim();
            }
        }
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
