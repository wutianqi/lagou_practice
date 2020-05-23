package com.lagou.sqlsession;

/**
 * 创建SqlSession的工厂类
 */
public interface SqlSessionFactory {

    SqlSession openSession();
}
