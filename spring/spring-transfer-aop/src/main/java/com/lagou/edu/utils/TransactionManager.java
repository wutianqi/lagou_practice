package com.lagou.edu.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

/**
 * 事务管理器
 */
@Component
public class TransactionManager {
    @Autowired
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
}
