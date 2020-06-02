package com.lagou.edu.utils;


import com.alibaba.druid.pool.DruidPooledConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 连接工具
 */
@Component
public class ConnectionUtil {
    private ThreadLocal<Connection> threadLocal = new ThreadLocal<>();
    @Autowired
    private DataSource dataSource;

    /**
     * 获取连接
     * @return
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        Connection connection = threadLocal.get();
        if(connection == null){
            Connection newConnection = dataSource.getConnection();
            threadLocal.set(newConnection);
            return newConnection;
        }

        return connection;
    }
}
