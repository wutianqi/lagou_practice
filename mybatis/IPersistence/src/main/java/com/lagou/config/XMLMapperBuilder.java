package com.lagou.config;

import com.lagou.enums.SqlCommandType;
import com.lagou.pojo.Configuration;
import com.lagou.pojo.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * 解析Mapper.xml的类
 */
public class XMLMapperBuilder {
    private Configuration configuration;

    public XMLMapperBuilder(Configuration configuration){
        this.configuration = configuration;
    }

    /**
     * 解析mapper.xml文件
     * @param in mapper.xml文件的输入流
     */
    public void parse(InputStream in) throws DocumentException {
        Document document = new SAXReader().read(in);
        Element mapperRoot = document.getRootElement();
        //获取Mapper中的select,insert,update,delete标签
        List<Element> sqlElements = (List<Element>)mapperRoot.selectNodes("select|insert|update|delete");
        for (Element element : sqlElements){
            String id = element.attributeValue("id");
            String parameterType = element.attributeValue("parameterType");
            String resultType = element.attributeValue("resultType");
            String sql = element.getTextTrim();
            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(id);
            mappedStatement.setParameterType(parameterType);
            mappedStatement.setResultType(resultType);
            mappedStatement.setSql(sql);
            mappedStatement.setSqlCommandType(getSqlCommandType(element.getName()));
            String key = mapperRoot.attributeValue("namespace") + "." + id;
            configuration.getStatementMap().put(key,mappedStatement);
        }
    }

    private SqlCommandType getSqlCommandType(String elementName) {
        switch (elementName){
            case "select":
                return SqlCommandType.SELECT;
            case "insert":
                return SqlCommandType.INSERT;
            case "update":
                return SqlCommandType.UPDATE;
            case "delete":
                return SqlCommandType.DELETE;
            default:
                return SqlCommandType.UN_KNOW;
        }
    }
}
