package com.lagou.sqlsession;

import com.lagou.config.XMLConfigBuilder;
import com.lagou.pojo.Configuration;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

/**
 * 解析配置文件并获得SqlSessionFactory
 */
public class SqlSessionFactoryBuilder {

    /**
     * 解析配置文件
     * @param in 配置文件输入流
     * @return
     */
    public SqlSessionFactory build(InputStream in) throws DocumentException, PropertyVetoException {
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        Configuration configuration = xmlConfigBuilder.parse(in);

        return new DefaultSqlSessionFactory(configuration);
    }

}
