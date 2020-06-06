package com.lagou.edu.utils;


import com.alibaba.druid.pool.DruidPooledConnection;
import com.lagou.edu.anno.Component;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 连接工具
 */
@Component
public class ConnectionUtil {
    private ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    /**
     * 获取连接
     * @return
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        Connection connection = threadLocal.get();
        if(connection == null){
            DruidPooledConnection newConnection = DruidUtils.getInstance().getConnection();
            threadLocal.set(newConnection);
            return newConnection;
        }

        return connection;
    }
}
