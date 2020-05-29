package com.lagou.sqlsession;

import com.lagou.enums.SqlCommandType;
import com.lagou.handler.MethodInvocationHandler;
import com.lagou.pojo.Configuration;
import com.lagou.pojo.MappedStatement;

import java.beans.IntrospectionException;
import java.lang.reflect.*;
import java.sql.SQLException;
import java.util.List;

/**
 * SqlSession的默认实现
 */
public class DefaultSqlSession implements SqlSession{
    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> selectList(String statementId, Object... params) throws SQLException, IllegalAccessException, InvocationTargetException, IntrospectionException, InstantiationException, NoSuchFieldException, NoSuchMethodException, ClassNotFoundException {
        MappedStatement mappedStatement = configuration.getStatementMap().get(statementId);
        return new SimpleExecutor().query(configuration, mappedStatement, params);
    }

    @Override
    public <T> T selectOne(String statementId, Object... params) throws SQLException, IllegalAccessException, IntrospectionException, InstantiationException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
        List<Object> objects = selectList(statementId, params);
        if(objects.size() == 0){
            return null;
        } else if(objects.size() == 1){
            return (T) objects.get(0);
        } else {
            throw new RuntimeException("more than one result");
        }
    }

    @Override
    public int update(String statementId, Object... params) throws ClassNotFoundException, SQLException, NoSuchFieldException, IllegalAccessException {
        return doUpdate(statementId, params);
    }

    @Override
    public int insert(String statementId, Object... params) throws ClassNotFoundException, SQLException, NoSuchFieldException, IllegalAccessException {
        return doUpdate(statementId, params);
    }

    @Override
    public int delete(String statementId, Object... params) throws ClassNotFoundException, SQLException, NoSuchFieldException, IllegalAccessException {
        return doUpdate(statementId, params);
    }

    @Override
    public <T> T getDao(Class<T> daoClazz) {
        //生成相应dao类型的代理对象，以实现对数据库的操作
        ClassLoader classLoader = this.getClass().getClassLoader();
        Class[] interfaces = {daoClazz};

        //创建实际调用方法的InvocationHandler
        MethodInvocationHandler invocationHandler = new MethodInvocationHandler(this, configuration);

        //返回代理对象
        return (T) Proxy.newProxyInstance(classLoader, interfaces, invocationHandler);
    }


    private int doUpdate(String statementId, Object[] params) throws ClassNotFoundException, SQLException, NoSuchFieldException, IllegalAccessException {
        //根据sql的全局唯一id获取MappedStatement
        MappedStatement mappedStatement = configuration.getStatementMap().get(statementId);

        //交给SimpleExecutor执行
        Executor executor = new SimpleExecutor();
        return executor.update(configuration, mappedStatement, params);
    }
}
