package com.lagou.io;

import java.io.InputStream;

/**
 * 加载资源的类
 */
public class Resources {

    /**
     * 加载配置文件
     * @param path 配置文件的路径
     * @return
     */
    public static InputStream getResourceAsStream(String path){
        return Resources.class.getResourceAsStream(path);
    }

}
