package com.lagou.edu.factory;

import com.lagou.edu.anno.Autowired;
import com.lagou.edu.anno.Transactional;
import com.lagou.edu.dao.AccountDao;
import com.lagou.edu.dao.impl.JdbcAccountDaoImpl;
import com.lagou.edu.utils.ClassUtil;
import com.lagou.edu.utils.TransactionManager;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Bean工厂
 */
public class BeanFactory {
    /**
     * ioc容器
     */
    private ConcurrentHashMap<String, Object> singletons = new ConcurrentHashMap<>();

    /**
     * 配置文件的路径
     */
    private String applicationContext;

    public BeanFactory(String applicationContext){
        this.applicationContext = applicationContext;
        refresh();
    }

    /**
     * 刷新容器，注册Bean
     */
    private void refresh() {
        //解析xml文件
        ClassLoader classLoader = BeanFactory.class.getClassLoader();
        InputStream in = classLoader.getResourceAsStream(applicationContext);
        SAXReader reader = new SAXReader();
        try {
            Document document = reader.read(in);
            //根元素
            Element rootElement = document.getRootElement();

            //注册xml中配置的Bean
            registerXmlBean(rootElement);

            //注册注解配置的Bean
            registerAnnoBean(rootElement);

            //给加了@Transactional注解的Bean进行事务配置
            postProcessBeanTx(rootElement);
        } catch (DocumentException | IOException | InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void postProcessBeanTx(Element rootElement) {
        //获取tx-driven标签
        List<Element> txDrivenElements = rootElement.selectNodes("//tx-driven");

        if(txDrivenElements.size() > 0){
            //事务管理器的BeanId
            String transactionManager = txDrivenElements.get(0).attributeValue("transactionManager");

            //所有的单例Bean的Entry
            Set<Map.Entry<String, Object>> entries = singletons.entrySet();
            for(Map.Entry<String, Object> entry : entries){
                String beanId = (String)entry.getKey();
                Object bean = entry.getValue();
                //获取类上的Transactional注解
                Transactional annotation = bean.getClass().getAnnotation(Transactional.class);
                if(annotation != null){
                    //存在Transactional注解则对Bean生成动态代理.如果bean有接口
                    //生成jdk动态代理，没接口生成cglib动态代理
                    Class<?>[] interfaces = bean.getClass().getInterfaces();
                    Object proxy;
                    ProxyFactory proxyFactory = new ProxyFactory((TransactionManager) singletons.get(transactionManager));
                    if(interfaces == null || interfaces.length < 1){
                        //无接口生成cglib动态代理
                        proxy = proxyFactory.getCglibProxy(bean);
                    } else {
                        //有接口生成jdk动态代理
                        proxy = proxyFactory.getJdkProxy(bean);
                    }
                    //代理类生成后，替换工厂中的bean
                    singletons.put(beanId, proxy);
                }
            }
        }
    }

    private void registerAnnoBean(Element rootElement) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //获取 component-scan注解的元素
        List<Element> componentScanElements = rootElement.selectNodes("//component-sacn");
        if(componentScanElements.size() > 0){
            Element componentScan = componentScanElements.get(0);
            //要扫描的包
            String scanPackages = componentScan.attributeValue("base-packages");
            //获取该包下所有的类
            Set<Class<?>> classes = ClassUtil.getClasses(scanPackages);

            //记录被加入到ioc容器中的类名
            List<String> addToIocClassName = new ArrayList<>();
            for(Class<?> clazz : classes){
                //获取类上所有的注解
                Annotation[] annotations = clazz.getAnnotations();
                for(Annotation annotation : annotations){
                    String simpleName = annotation.annotationType().getSimpleName();
                    //如果类被标上 @Component，@Service，@Repository则实例化该类，并放入ioc容器中
                    if(simpleName.equals("Component") || simpleName.equals("Service") || simpleName.equals("Repository")){
                        //获取注解的属性名
                        String attrValue = (String)annotation.annotationType().getMethod("value").invoke(annotation);
                        if(StringUtils.isBlank(attrValue)){
                            //如果value为空，则取类名首字母小写
                            String classSimpleName = clazz.getSimpleName();
                            attrValue = classSimpleName.substring(0,1).toLowerCase() + classSimpleName.substring(1, classSimpleName.length());
                        }

                        //实例化Bean
                        Object bean = clazz.getDeclaredConstructor().newInstance();
                        singletons.put(attrValue, bean);
                        addToIocClassName.add(attrValue);
                    }
                }
            }

            //Bean创建完成后，对Bean的属性进行自动注入
            for(String beanName : addToIocClassName){
                Object bean = singletons.get(beanName);
                //获取该类所有的字段
                Field[] declaredFields = bean.getClass().getDeclaredFields();
                for(Field field : declaredFields){
                    //暴力访问
                    field.setAccessible(true);
                    //获取自动注入的注解
                    Autowired annotation = field.getAnnotation(Autowired.class);
                    if(annotation != null){
                        //存在Autowired注解
                        //从ioc容器中获取字段对应的bean，并注入
                        Class<?> fieldType = field.getType();
                        Collection<Object> values = singletons.values();
                        for(Object singleton : values){
                            //遍历容器中所有的Bean
                            if(singleton.getClass().isAssignableFrom(fieldType) || Arrays.asList(singleton.getClass().getInterfaces()).contains(fieldType)){
                                //如果容器中的Bean的类型和字段声明的类型相同或者是字段声明的类型的子类
                                field.set(bean, singleton);
                            }
                        }
                    }
                }
            }
        }
    }

    private void registerXmlBean(Element rootElement) {
        try {
            //拿到所有的bean元素
            List<Element> beanElements = rootElement.selectNodes("//bean");
            for(Element beanElement : beanElements){
                String beanId = beanElement.attributeValue("id");
                String beanClass = beanElement.attributeValue("class");
                //创建bean对象
                Object beanObj = Class.forName(beanClass).newInstance();
                singletons.put(beanId, beanObj);
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
                Object parentObj = singletons.get(parentId);
                //获取该类的所有方法，取到设置该属性的方法
                Method[] methods = parentObj.getClass().getMethods();
                for(Method method : methods){
                    if(method.getName().equalsIgnoreCase("set" + name)){ //这里为了简单，忽略大小写。
                        //给父类对象设置属性
                        method.invoke(parentObj, singletons.get(ref));
                        //将设置属性的对象放入到beanFactory当中
                        singletons.put(parentId, parentObj);
                    }
                }
            }
        }catch (IllegalAccessException | InstantiationException | ClassNotFoundException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    /**
     * 提供获取Bean对象的方法
     * @param beanId bean的id
     * @return
     */
    public Object getBean(String beanId){
        return singletons.get(beanId);
    }


}
