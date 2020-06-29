package com.lagou.server.component;

import java.util.List;

/**
 * url请求映射组件
 * @author wuqi
 * @date 2020-06-29 8:35
 */
public class Mapper {
    //虚拟机主机
    private List<MappedHost> mappedHosts;

    public List<MappedHost> getMappedHosts() {
        return mappedHosts;
    }

    public void setMappedHosts(List<MappedHost> mappedHosts) {
        this.mappedHosts = mappedHosts;
    }
}
