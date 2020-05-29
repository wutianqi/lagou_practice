package com.lagou.sqlsession;

import com.lagou.config.BoundSql;
import com.lagou.pojo.Configuration;
import com.lagou.pojo.MappedStatement;
import com.lagou.utils.GenericTokenParser;
import com.lagou.utils.ParameterMapping;
import com.lagou.utils.ParameterMappingTokenHandler;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Executor的默认实现类
 */
public class SimpleExecutor implements Executor{

    /**
     * 真正执行sql语句的地方
     * @param configuration 配置
     * @param mappedStatement 要执行的sql对应的MappedStatement
     * @param params 参数
     * @return
     */
    public <T> T query(Configuration configuration, MappedStatement mappedStatement, Object[] params) throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException, IntrospectionException {
        //下面开始jdbc查询数据的模式
        //1.加载驱动，获取数据库连接
        Connection connection = configuration.getDataSource().getConnection();

        //2.获取PreparedStatement
        //sql格式为 SELECT * FROM user WHERE id = #{id} AND user_name = #{username} 这种形式，需要对其进行解析。
        //1.将#{}替换为? 2.记录下#{}里面的值并存储起来，以便后面从参数进行取值。
        String sql = mappedStatement.getSql();
        BoundSql boundSql = getBoundSql(sql);
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSql());

        //3.参数设置
        setParam(boundSql, params, mappedStatement, preparedStatement);

        //4.执行sql语句
        ResultSet resultSet = preparedStatement.executeQuery();

        //5.封装返回结果
        String resultType = mappedStatement.getResultType();
        Class<?> resultTypeClass = getClassType(resultType);
        List<Object> resultList = new ArrayList<>();
        while (resultSet.next()){
            //获取结果的元信息
            ResultSetMetaData metaData = resultSet.getMetaData();
            Object resultTypeObj = resultTypeClass.getDeclaredConstructor().newInstance();
            int columnCount = metaData.getColumnCount();
            for(int i=1;i<=columnCount;i++){
                //获取列名
                String columnName = metaData.getColumnName(i);
                //将列名设置到返回对象中，这里也需要用到反射
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultTypeClass);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(resultTypeObj, resultSet.getObject(columnName));
            }

            resultList.add(resultTypeObj);
        }

        return (T) resultList;
    }

    @Override
    public int update(Configuration configuration, MappedStatement mappedStatement, Object[] params) throws SQLException, IllegalAccessException, NoSuchFieldException, ClassNotFoundException {
        //获取数据库连接
        Connection connection = configuration.getDataSource().getConnection();

        //解析sql语句中的占位符，获取BoundSql
        String sql = mappedStatement.getSql();
        BoundSql boundSql = getBoundSql(sql);

        //获取PreparedStatement
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSql());

        //设置参数
        setParam(boundSql, params, mappedStatement, preparedStatement);

        //执行sql语句,获取返回值
        int affectRows = preparedStatement.executeUpdate();

//        connection.commit();

        return affectRows;
    }

    private void setParam(BoundSql boundSql, Object[] params, MappedStatement mappedStatement, PreparedStatement preparedStatement) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException, SQLException {
        String parameterType = mappedStatement.getParameterType();
        //参数的类型
        Class<?> parameterTypeClass = getClassType(parameterType);
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
        for(int i=0;i<parameterMappingList.size();i++){
            ParameterMapping parameterMapping = parameterMappingList.get(i);
            //获取到参数的名字，我们需要拿到该参数在对象中的值。需要通过反射实现。
            String parameterName = parameterMapping.getContent();
            Field field = parameterTypeClass.getDeclaredField(parameterName);
            field.setAccessible(true);
            Object paramValue = field.get(params[0]);
            preparedStatement.setObject(i+1, paramValue);
        }
    }

    private Class<?> getClassType(String parameterType) throws ClassNotFoundException {
        if(parameterType != null && !"".equals(parameterType)){
            return Class.forName(parameterType);
        }

        return null;
    }

    /**
     * 解析sql语句
     * @param sql sql语句
     * @return
     */
    private BoundSql getBoundSql(String sql) {
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        //占位符解析器，需配合ParameterMappingTokenHandler来对占位符进行解析。
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        String parseSql = genericTokenParser.parse(sql);
        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();
        return new BoundSql(parseSql, parameterMappings);
    }
}
