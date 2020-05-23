package com.lagou.config;

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
        List<Element> selectElements = (List<Element>)mapperRoot.selectNodes("select");
        for (Element element : selectElements){
            String id = element.attributeValue("id");
            String parameterType = element.attributeValue("parameterType");
            String resultType = element.attributeValue("resultType");
            String sql = element.getTextTrim();
            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(id);
            mappedStatement.setParameterType(parameterType);
            mappedStatement.setResultType(resultType);
            mappedStatement.setSql(sql);
            String key = mapperRoot.attributeValue("namespace") + "." + id;
            configuration.getStatementMap().put(key,mappedStatement);
        }
    }
}
