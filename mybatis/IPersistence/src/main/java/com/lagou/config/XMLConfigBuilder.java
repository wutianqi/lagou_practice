package com.lagou.config;

import com.lagou.io.Resources;
import com.lagou.pojo.Configuration;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * 解析SqlMapConfig的类
 */
public class XMLConfigBuilder {

    private Configuration configuration;

    public XMLConfigBuilder(){
        this.configuration = new Configuration();
    }

    /**
     * 解析配置文件
     * @param in
     * @return
     */
    public Configuration parse(InputStream in) throws DocumentException, PropertyVetoException {
        //解析配置文件，创建连接池
        Document document = new SAXReader().read(in);
        Element configurationElement = document.getRootElement();
        List<Element> propertyElements = (List<Element>)configurationElement.selectNodes("//property");
        Properties properties = new Properties();
        for(Element element : propertyElements){
            String propertyName = element.attributeValue("name");
            String propertyValue = element.attributeValue("value");
            properties.put(propertyName, propertyValue);
        }
        //连接池
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass((String) properties.get("driverClass"));
        dataSource.setJdbcUrl((String) properties.get("jdbcUrl"));
        dataSource.setUser((String) properties.get("username"));
        dataSource.setPassword((String) properties.get("password"));
        configuration.setDataSource(dataSource);

        //解析配置文件，创建MappedStatement列表
        List<Element> mapperElements = (List<Element>)configurationElement.selectNodes("//mapper");
        XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(configuration);
        for(Element element : mapperElements){
            InputStream mapperIn = Resources.getResourceAsStream(element.attributeValue("resource"));
            xmlMapperBuilder.parse(mapperIn);
        }

        return configuration;
    }
}
