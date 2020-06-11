package com.lagou.edu.mvcframework.servlet;

import com.lagou.edu.mvcframework.annotations.Autowired;
import com.lagou.edu.mvcframework.annotations.Controller;
import com.lagou.edu.mvcframework.annotations.RequestMapping;
import com.lagou.edu.mvcframework.annotations.Service;
import com.lagou.edu.mvcframework.handlerMapping.HandlerMapping;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * 自定义DispatcherServlet
 * @author wuqi
 * @date 2020-06-10 12:11
 */
public class DispatcherServlet extends HttpServlet {
    /**
     * 这里使用Properties作为配置文件
     */
    private Properties properties;

    /**
     * 扫描的包下的name集合
     */
    private List<String> classNames = new ArrayList<>();

    /**
     * ioc容器
     */
    private Map<String, Object> singletons = new ConcurrentHashMap<>();

    /**
     * 映射器
     */
    private List<HandlerMapping> handlerMappings = new ArrayList<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        try {
            //1.加载配置文件
            loadConfig(config);

            //2.扫描包，并将类名存储在集合中
            String basePackages = (String)properties.get("basePackages");
            scanPackages(basePackages);

            //3.进行bean的实例化
            newInstanceBean();

            //4.bean的依赖注入
            autowiredBean();

            //5.获取url和handler方法之间的映射关系
            createHandlerMapping();

            System.out.println("springmvc初始化成功...");
        } catch (IOException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    private void createHandlerMapping() {
        if(singletons.size() == 0){return;}

        for(Map.Entry<String, Object> entry : singletons.entrySet()){
            Object bean = entry.getValue();
            Class<?> beanClass = bean.getClass();
            if(!beanClass.isAnnotationPresent(Controller.class)){continue;}

            RequestMapping classRequestMappingAnnotation = beanClass.getAnnotation(RequestMapping.class);
            //父类@RequestMapping中的值
            String classRequestMapping = classRequestMappingAnnotation.value();

            Method[] methods = beanClass.getMethods();
            for(Method method : methods){
                if(!method.isAnnotationPresent(RequestMapping.class)){continue;}

                RequestMapping methodRequestMappingAnnotation = method.getAnnotation(RequestMapping.class);
                String methodRequestMapping = methodRequestMappingAnnotation.value();
                if(StringUtils.isBlank(methodRequestMapping)){continue;}

                //url
                String requestMapping = classRequestMapping + methodRequestMapping;
                Pattern urlPattern = Pattern.compile(requestMapping);

                //方法参数
                Parameter[] parameters = method.getParameters();
                Map<Integer, String> parameterMapping = new HashMap<>();
                if(parameters.length > 0){
                    for (int i = 0; i < parameters.length; i++) {
                        if(parameters[i].getType().getName().equals(HttpServletRequest.class.getName())){
                            parameterMapping.put(i, HttpServletRequest.class.getName());
                        } else if(parameters[i].getType().getName().equals(HttpServletResponse.class.getName())){
                            parameterMapping.put(i, HttpServletResponse.class.getName());
                        } else {
                            parameterMapping.put(i, parameters[i].getName());
                        }
                    }
                }

                HandlerMapping handlerMapping = new HandlerMapping(method, bean, urlPattern, parameterMapping);
                handlerMappings.add(handlerMapping);
            }
        }
    }

    private void autowiredBean() throws IllegalAccessException {
        if(singletons.size() == 0){return;}

        for(Map.Entry<String, Object> entry : singletons.entrySet()){
            Object bean = entry.getValue();
            Field[] fields = bean.getClass().getDeclaredFields();
            if(fields.length == 0){return;}
            for(Field field : fields){
                boolean autoWiredAnnotationPresent = field.isAnnotationPresent(Autowired.class);
                if(autoWiredAnnotationPresent){
                    field.setAccessible(true);
                    boolean isInterface = field.getType().isInterface();
                    if(isInterface){
                        //如果是接口直接按照类型注入
                        field.set(bean, singletons.get(field.getClass().getName()));
                    } else {
                        //不是接口，则按照名字注入
                        Autowired annotation = field.getAnnotation(Autowired.class);
                        String beanId = annotation.value();
                        if(StringUtils.isBlank(beanId)){
                            beanId = field.getName();
                        }
                        Object fieldBean = singletons.get(beanId);
                        field.set(bean, fieldBean);
                    }
                }
            }
        }
    }

    private void newInstanceBean() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if(classNames.size() == 0){return;}

        for(String className : classNames){
            Class<?> clazz = Class.forName(className);
            boolean controllerAnnotationPresent = clazz.isAnnotationPresent(Controller.class);
            if(controllerAnnotationPresent){
                //对于Controller，就把类名当作beanId，并且首字母小写
                Object o = clazz.getDeclaredConstructor().newInstance();
                String beanId = firstToLower(clazz.getSimpleName());
                singletons.put(beanId, o);
            }

            boolean serviceAnnotationPresent = clazz.isAnnotationPresent(Service.class);
            if(serviceAnnotationPresent){
                //对于service，不仅要实现类的beanName对应的关系存储在容器中，还需要把接口的全限定类名对应的关系存在容器中
                //对于@Service注解，如果注解写了value，那么以value值作为beanId
                Object o = clazz.getDeclaredConstructor().newInstance();
                Service annotation = clazz.getAnnotation(Service.class);
                String beanId = annotation.value();
                if(StringUtils.isNotBlank(beanId)){
                    singletons.put(beanId, o);
                } else {
                    singletons.put(firstToLower(clazz.getSimpleName()), o);
                }

                //接口全限定名-对象
                Class<?>[] interfaces = clazz.getInterfaces();
                if(interfaces.length == 0){continue;}
                for(Class<?> c : interfaces){
                    singletons.put(c.getName(), o);
                }
            }
        }
    }

    private String firstToLower(String simpleName) {
        return simpleName.substring(0,1).toLowerCase() + simpleName.substring(1, simpleName.length());
    }

    private void scanPackages(String scanPackages) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //classpath的根路径 D:/software%20workplace/git%20workpalce/lagou/lagou_practice/springmvc/my-mvc/target/classes/
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath().replaceAll("%20", " "); //处理目录为空格的问题
        //包的文件路径
        String packagePath = path + scanPackages.replaceAll("\\.", "/");
        File packageDir = new File(packagePath);
        File[] files = packageDir.listFiles();
        if(files == null || files.length == 0){return;}

        for(File file : files){
            if(file.isDirectory()){
                //是目录的话，递归解析
                scanPackages(scanPackages + "." + file.getName());
            }else {
                String className = scanPackages + "." + file.getName().replace(".class", "");
                classNames.add(className);
            }
        }
    }

    private void loadConfig(ServletConfig config) throws IOException {
        String contextConfigLocation = config.getInitParameter("contextConfigLocation");
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(contextConfigLocation);
        if(in != null){
            properties = new Properties();
            properties.load(in);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取url对应的Handler，取出请求中所带参数，并设置到Handler方法中，调用handler的方法
        if(handlerMappings.size() == 0){return;}
        String requestURI = req.getRequestURI();
        System.out.println(requestURI);
        for(HandlerMapping handlerMapping : handlerMappings){
            if(!handlerMapping.getUrlPattern().matcher(requestURI).matches()){continue;}

            //匹配上，则进行参数封装及调用
            Map<Integer, String> parameterMapping = handlerMapping.getParameterMapping();
            Object[] args = null;
            if(parameterMapping.entrySet().size() > 0){
                args = new Object[parameterMapping.entrySet().size()];
                for(Map.Entry<Integer, String> entry : parameterMapping.entrySet()){
                    Integer index = entry.getKey();
                    String name = entry.getValue();
                    String[] parameterValues = req.getParameterValues(name);
                    String parameter = null;
                    if(parameterValues != null && parameterValues.length > 0){
                        parameter = "";
                        for(String parameterValue : parameterValues){
                            parameter = parameter + " " + parameterValue;
                        }
                    }
                    if(StringUtils.isNotBlank(parameter)){
                        args[index] = parameter;
                    } else if(name.equals(HttpServletRequest.class.getName())){
                        args[index] = req;
                    } else  if(name.equals(HttpServletResponse.class.getName())){
                        args[index] = resp;
                    } else {
                        args[index] = null;
                    }
                }
            }

            try {
                handlerMapping.getHandler().invoke(handlerMapping.getBean(), args);

                req.getRequestDispatcher("/index.jsp").forward(req, resp);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
