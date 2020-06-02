package com.lagou.edu.factory;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

/**
 * Bean工厂
 */
public class BeanFactory {
    //存储bean的map集合
    private static HashMap beanMap = new HashMap();

    //在类初始化是就解析xml文件并将对象创建存储到对象工厂当中
    static {
        //解析xml文件
        ClassLoader classLoader = BeanFactory.class.getClassLoader();
        InputStream in = classLoader.getResourceAsStream("beans.xml");
        SAXReader reader = new SAXReader();
        try {
            Document document = reader.read(in);
            //根元素
            Element rootElement = document.getRootElement();
            //拿到所有的bean元素
            List<Element> beanElements = rootElement.selectNodes("//bean");
            for(Element beanElement : beanElements){
                String beanId = beanElement.attributeValue("id");
                String beanClass = beanElement.attributeValue("class");
                //创建bean对象
                Object beanObj = Class.forName(beanClass).newInstance();
                beanMap.put(beanId, beanObj);
                System.out.println(beanId + "对象加入到beanFactory");
            }

            //解析各个对象的依赖，并调用get方法将其注入
            //获取所有property节点
            List<Element> propertyElements = rootElement.selectNodes("//property");
            for(Element propertyElement : propertyElements){
                //解析属性
                String name = propertyElement.attributeValue("name");
                String ref = propertyElement.attributeValue("ref");
                //获取父节点，父节点即为属性所在的类。并拿到对象
                Element parentElement = propertyElement.getParent();
                String parentId = parentElement.attributeValue("id");
                Object parentObj = beanMap.get(parentId);
                //获取该类的所有方法，取到设置该属性的方法
                Method[] methods = parentObj.getClass().getMethods();
                for(Method method : methods){
                    if(method.getName().equalsIgnoreCase("set" + name)){ //这里为了简单，忽略大小写。
                        //给父类对象设置属性
                        method.invoke(parentObj, beanMap.get(ref));
                        //将设置属性的对象放入到beanFactory当中
                        beanMap.put(parentId, parentObj);
                    }
                }
            }
        } catch (DocumentException | IllegalAccessException | InstantiationException | ClassNotFoundException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 提供获取Bean对象的方法
     * @param beanId bean的id
     * @return
     */
    public static Object getBean(String beanId){
        return beanMap.get(beanId);
    }


}
