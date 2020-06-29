package com.lagou.server.component;

import com.lagou.server.Servlet;

import java.util.List;

/**
 * Context应用组件
 * @author wuqi
 * @date 2020-06-29 7:15
 */
public class MappedContext {
    //应用名
    private String name;
    //servlet包装类数组
    private List<MappedWrapper> mappedWrappers;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MappedWrapper> getMappedWrappers() {
        return mappedWrappers;
    }

    public void setMappedWrappers(List<MappedWrapper> mappedWrappers) {
        this.mappedWrappers = mappedWrappers;
    }
}
