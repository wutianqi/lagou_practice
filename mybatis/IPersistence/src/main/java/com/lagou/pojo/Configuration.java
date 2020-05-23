package com.lagou.pojo;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 封装SqlMapConfig.xml的容器
 */
public class Configuration {

    /**
     * 数据源
     */
    private DataSource dataSource;

    /**
     * MappedStatement Map的key是 statementId(namespace.id) value就是MappedStatement
     */
    private Map<String,MappedStatement> statementMap = new HashMap<String, MappedStatement>();




    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Map<String, MappedStatement> getStatementMap() {
        return statementMap;
    }

    public void setStatementMap(Map<String, MappedStatement> statementMap) {
        this.statementMap = statementMap;
    }
}
