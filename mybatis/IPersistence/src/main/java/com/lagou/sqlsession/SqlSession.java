package com.lagou.sqlsession;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * sql会话
 */
public interface SqlSession {
    /**
     * 查询多个
     * @param statementId sql语句的全局唯一id
     * @param params 参数
     * @return
     */
    public <E> List<E> selectList(String statementId, Object... params) throws SQLException, IllegalAccessException, InvocationTargetException, IntrospectionException, InstantiationException, NoSuchFieldException, NoSuchMethodException, ClassNotFoundException;

    /**
     * 查询多个
     * @param statementId sql语句的全局唯一id
     * @param params 参数
     * @return
     */
    public <T> T selectOne(String statementId, Object... params) throws SQLException, IllegalAccessException, IntrospectionException, InstantiationException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException;

    /**
     * 获取指定的Dao代理对象
     * @param daoClazz dao的Class对象
     * @return
     */
    public <T> T getDao(Class<T> daoClazz);
}
