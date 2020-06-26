package com.lagou.server;

import java.io.IOException;

/**
 * Servlet接口类
 * @author wuqi
 * @date 2020-06-26 9:26
 */
public interface Servlet {
    /**
     * 初始化方法
     */
    void init();

    /**
     * 销毁方法
     */
    void destroy();

    /**
     * 服务方法
     */
    void service(Request request, Response response) throws IOException;

}
