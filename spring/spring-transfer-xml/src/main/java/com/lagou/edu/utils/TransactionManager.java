package com.lagou.edu.utils;

import java.sql.SQLException;

/**
 * 事务管理器
 */
public class TransactionManager {

    private ConnectionUtil connectionUtil;

    /**
     * 开始事务
     * @throws SQLException
     */
    public void begin() throws SQLException {
        connectionUtil.getConnection().setAutoCommit(false);
    }

    /**
     * 提交事务
     * @throws SQLException
     */
    public void commit() throws SQLException {
        connectionUtil.getConnection().commit();
    }

    /**
     * 回滚事务
     * @throws SQLException
     */
    public void rollback() throws SQLException {
        connectionUtil.getConnection().rollback();
    }

    public ConnectionUtil getConnectionUtil() {
        return connectionUtil;
    }

    public void setConnectionUtil(ConnectionUtil connectionUtil) {
        this.connectionUtil = connectionUtil;
    }
}
