package com.lagou.server;

import java.io.*;

/**
 * 静态资源工具类
 * @author wuqi
 * @date 2020-06-25 22:57
 */
public class StaticResourceUtil {

    /**
     * 获取资源的绝对路径
     */
    public static String getAbsolutePath(String uri){
       String abStr = StaticResourceUtil.class.getResource("/").getPath() + uri;
       return abStr.replaceAll("%20", " ");
    }

    /**
     * 输出静态资源
     */
    public static void outPutStaticResource(FileInputStream fileInputStream, OutputStream outputStream) throws IOException {
        int available = fileInputStream.available();
        byte[] buffer = new byte[1024];

        int written = 0;

        outputStream.write(HttpProtocolUtil.get200Header(available).getBytes());
        while (written < available){
            if(written + 1024 > available){ //剩余的数据不足1024，则获取剩余的长度进行读取
                buffer = new byte[available - written];
            }

            fileInputStream.read(buffer);
            outputStream.write(buffer);
            outputStream.flush();
            written += buffer.length;
        }
    }
}
