package com.lagou.server;

import java.io.*;

/**
 * 响应
 * @author wuqi
 * @date 2020-06-25 22:52
 */
public class Response {

    private OutputStream outputStream;

    /**
     * 输出响应
     */
    public void outPutHtml(String uri) throws IOException {
        String absolutePath = StaticResourceUtil.getAbsolutePath(uri);


        File file = new File(absolutePath);
        if(file.exists() && !file.isDirectory()){
            //文件存在且不为目录时输出
            FileInputStream fileInputStream = new FileInputStream(file);
            StaticResourceUtil.outPutStaticResource(fileInputStream, outputStream);
        }else {
            output(HttpProtocolUtil.get400Header());
        }
    }

    private void output(String content) throws IOException {
        outputStream.write(content.getBytes());
        outputStream.flush();
    }


    public Response(){

    }

    public Response(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }
}
