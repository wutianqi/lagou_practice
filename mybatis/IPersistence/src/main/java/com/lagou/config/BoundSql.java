package com.lagou.config;

import com.lagou.utils.ParameterMapping;

import java.util.List;

/**
 * sql语句解析后，解析的语句以及相应占位符的存储
 */
public class BoundSql {
    private String sql;

    private List<ParameterMapping> parameterMappingList;

    public BoundSql(String sql, List<ParameterMapping> parameterMappings) {
        this.sql = sql;
        this.parameterMappingList = parameterMappings;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<ParameterMapping> getParameterMappingList() {
        return parameterMappingList;
    }

    public void setParameterMappingList(List<ParameterMapping> parameterMappingList) {
        this.parameterMappingList = parameterMappingList;
    }
}
