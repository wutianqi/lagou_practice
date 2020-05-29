package com.lagou.pojo;

import com.lagou.enums.SqlCommandType;

/**
 * 封装mapper.xml的容器对象
 */
public class MappedStatement {
    /**
     * id
     */
    private String id;

    /**
     * 参数类型
     */
    private String parameterType;

    /**
     * 返回值类型
     */
    private String resultType;

    /**
     * sql
     */
    private String sql;

    /**
     * sql命令类型
     */
    private SqlCommandType sqlCommandType;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public SqlCommandType getSqlCommandType() {
        return sqlCommandType;
    }

    public void setSqlCommandType(SqlCommandType sqlCommandType) {
        this.sqlCommandType = sqlCommandType;
    }
}
