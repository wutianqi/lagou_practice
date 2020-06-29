package com.lagou.server.component;

import java.util.List;

/**
 * Host组件
 * @author wuqi
 * @date 2020-06-29 7:15
 */
public class MappedHost {
    //虚拟主机名
    private String name;
    //应用
    private List<MappedContext> mappedContexts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MappedContext> getMappedContexts() {
        return mappedContexts;
    }

    public void setMappedContexts(List<MappedContext> mappedContexts) {
        this.mappedContexts = mappedContexts;
    }
}
