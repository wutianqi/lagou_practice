package com.lagou.sqlsession;

import com.lagou.pojo.Configuration;
import com.lagou.pojo.MappedStatement;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

/**
 * 执行器
 */
public interface Executor {
    /**
     * 查询
     * @param configuration 配置类
     * @param mappedStatement sql语句对象
     * @param params 参数
     * @return
     * @author wuqi
     * @date 2020-05-29 12:11:08
     */
    <T> T query(Configuration configuration, MappedStatement mappedStatement, Object[] params) throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException, IntrospectionException;

    /**
     * 更新
     *
     * @param configuration 配置类
     * @param mappedStatement sql映射语句对象
     * @param params 参数
     * @return
     */
    int update(Configuration configuration, MappedStatement mappedStatement, Object[] params) throws SQLException, IllegalAccessException, NoSuchFieldException, ClassNotFoundException;
}
