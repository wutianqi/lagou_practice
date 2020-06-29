package com.lagou.server.classloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * web应用ClassLoader
 * 模拟tomcat的类加载机制
 * 2.从应用下找
 * 3.去类路径下找
 * @author wuqi
 * @date 2020-06-29 14:31
 */
public class WebApplicationClassloader extends ClassLoader {
    //应用根路径
    private String docBase;


    public WebApplicationClassloader(String docBase){
        //BootStrap为父加载器
        this.docBase = docBase;
    }


    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        //从当前应用中查找D:\tomcat\webapps\demo1/server/LagouServlet
        String classFilePath = docBase + "/" + name.replaceAll("\\.", "/") + ".class";
        File classFile = new File(classFilePath);
        if(classFile.exists()){
            try {
                FileInputStream inputStream = new FileInputStream(classFile);
                int available = inputStream.available();
                byte[] classBuffer = new byte[available];
                int read = inputStream.read(classBuffer);

                return defineClass(name, classBuffer, 0, read);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //从类路径下加载
        return ClassLoader.getSystemClassLoader().loadClass(name);
    }
}
