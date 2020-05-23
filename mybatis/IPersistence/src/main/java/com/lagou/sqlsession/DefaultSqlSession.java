package com.lagou.sqlsession;

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


    public <E> List<E> selectList(String statementId, Object... params) throws SQLException, IllegalAccessException, InvocationTargetException, IntrospectionException, InstantiationException, NoSuchFieldException, NoSuchMethodException, ClassNotFoundException {
        MappedStatement mappedStatement = configuration.getStatementMap().get(statementId);
        return new SimpleExecutor().query(configuration, mappedStatement, params);
    }

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
    public <T> T getDao(Class<T> daoClazz) {
        //生成相应dao类型的代理对象，以实现对数据库的操作
        ClassLoader classLoader = this.getClass().getClassLoader();
        Class[] interfaces = {daoClazz};
        InvocationHandler invocationHandler = new InvocationHandler() {
            /**
             * 执行代理对象时，实际调用的方法
             * @param proxy 生成的代理对象
             * @param method 执行的目标方法
             * @param args 参数列表
             * @return
             */
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                /*
                    invoke底层实际还是需要调用jdbc的方法，我们可以调用SimpleExecutor的方法来实现，
                    为了使用SimpleExecutor的方法，我们需要知道我们所需要调用的StatementId，所以
                    我们这里必须将Dao的类名和调用的方法名称和mapper.xml中的namespace以及其中sql语句
                    的id对应起来。即 类名.方法名=namespace.id。
                    至于我们该调用select还是selectList，我们根据方法是否有泛型来决定，如果没有返回则
                    认为是select，有泛型则认为是selectList
                 */
                String statementId = method.getDeclaringClass().getName() + "." + method.getName();
                Type genericReturnType = method.getGenericReturnType();
                //判断是否进行了 泛型类型参数化
                if(genericReturnType instanceof ParameterizedType){
                   return selectList(statementId, args);
                }

                return selectOne(statementId, args);
            }
        };
        return (T) Proxy.newProxyInstance(classLoader, interfaces, invocationHandler);
    }
}
