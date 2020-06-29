package com.lagou.server.component;

import org.dom4j.DocumentException;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Server组件
 * @author wuqi
 * @date 2020-06-29 7:12
 */
public class Server {
    //service服务
    private List<Service> services;

    /**
     * 启动
     */
    public void start() throws Exception{
        for(Service service : services){
            service.start();
        }
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }
}
