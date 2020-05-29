package com.lagou.handler;

import com.lagou.enums.SqlCommandType;
import com.lagou.pojo.Configuration;
import com.lagou.pojo.MappedStatement;
import com.lagou.sqlsession.SqlSession;

import java.beans.IntrospectionException;
import java.lang.reflect.*;
import java.sql.SQLException;

/**
 * sql方法调用器
 */
public class MethodInvocationHandler implements InvocationHandler {
    private SqlSession sqlSession;
    private Configuration configuration;

    public MethodInvocationHandler(SqlSession sqlSession, Configuration configuration){
        this.sqlSession = sqlSession;
        this.configuration = configuration;
    }

    /**
     * 执行Mapper代理对象时，实际调用的方法
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
                    至于我们该调用增删改查中的哪一个，我们可以根据解析的MappedStatement中的SqlCommandType
                   来确定。而查询是查询单个还是查询所有，可以根据返回参数的泛型来判断，有泛型则返回
                   多个，没泛型则仅返回单个。
                 */
        String statementId = method.getDeclaringClass().getName() + "." + method.getName();
        MappedStatement mappedStatement = configuration.getStatementMap().get(statementId);
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        switch (sqlCommandType){
            case SELECT:
                //查询
                return select(method, statementId, args);
            case INSERT:
                //插入
                return sqlSession.insert(statementId, args);
            case UPDATE:
                //插入
                return sqlSession.update(statementId, args);
            case DELETE:
                //插入
                return sqlSession.delete(statementId, args);
            default:
                return null;
        }
    }

    private Object select(Method method, String statementId, Object[] args) throws IllegalAccessException, ClassNotFoundException, IntrospectionException, InstantiationException, SQLException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
        Type genericReturnType = method.getGenericReturnType();
        //判断是否进行了 泛型类型参数化
        if(genericReturnType instanceof ParameterizedType){
            return sqlSession.selectList(statementId, args);
        }

        return sqlSession.selectOne(statementId, args);
    }

}
