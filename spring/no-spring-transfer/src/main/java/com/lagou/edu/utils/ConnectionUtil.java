package com.lagou.edu.utils;


import com.alibaba.druid.pool.DruidPooledConnection;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 连接工具
 */
public class ConnectionUtil {
    //让工厂管理Bean
//    private ConnectionUtil(){}
//    public static ConnectionUtil getInstance(){
//        return connectionUtil;
//    }
//    private static ConnectionUtil connectionUtil = new ConnectionUtil();

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
