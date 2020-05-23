package com.lagou.sqlsession;

import com.lagou.pojo.Configuration;

/**
 * SqlSessionFactory默认实现类
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory{
    private Configuration configuration;


    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}
