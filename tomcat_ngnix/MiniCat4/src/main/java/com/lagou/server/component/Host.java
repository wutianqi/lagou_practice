package com.lagou.server.component;

/**
 * Host组件
 * @author wuqi
 * @date 2020-06-29 13:34
 */
public class Host {
    //虚拟主机名
    private String name;
    //应用根路径
    private String appBase;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppBase() {
        return appBase;
    }

    public void setAppBase(String appBase) {
        this.appBase = appBase;
    }
}
