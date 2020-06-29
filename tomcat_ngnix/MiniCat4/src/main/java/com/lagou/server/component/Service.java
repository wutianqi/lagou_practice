package com.lagou.server.component;

import com.lagou.server.HttpServlet;
import com.lagou.server.classloader.WebApplicationClassloader;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Service组件
 * @author wuqi
 * @date 2020-06-29 7:13
 */
public class Service {
    //连接组件
    private List<Connector> connectors;

    //虚拟主机名
    private List<Host> hosts;

    //url映射组件
    private Mapper mapper = new Mapper();

    public void start() throws Exception {
        //初始化mapper
        loadMapper(mapper);

        //启动连接
        for(Connector connector : connectors){
            connector.start();
        }
    }

    private void loadMapper(Mapper mapper) throws Exception{
        //去各个虚拟机主机下加载每个应用以及Servlet
        List<MappedHost> mappedHosts = new ArrayList<>();
        mapper.setMappedHosts(mappedHosts);

        for(Host host : hosts){
            //初始化host
            MappedHost mappedHost = loadMapperHost(host);
            mappedHosts.add(mappedHost);
        }
    }

    private MappedHost loadMapperHost(Host host) throws Exception {
        //host
        MappedHost mappedHost = new MappedHost();
        mappedHost.setName(host.getName());
        //context
        List<MappedContext> mappedContexts = new ArrayList<>();
        mappedHost.setMappedContexts(mappedContexts);

        //获取各个虚拟主机下的各个应用名
        String appBase = host.getAppBase();
        File appBaseFile = new File(appBase);
        //当前目录下的所有文件/目录
        File[] contextFiles = appBaseFile.listFiles();
        if(contextFiles != null){
            for(File contextFile : contextFiles){
                if(contextFile.isDirectory()){
                    //初始化MapperContext
                    MappedContext mappedContext = loadMapperContext(contextFile);
                    mappedContexts.add(mappedContext);
                }
            }
        }

        return mappedHost;
    }

    private MappedContext loadMapperContext(File contextFile) throws Exception {
        //context
        MappedContext mappedContext = new MappedContext();
        mappedContext.setName(contextFile.getName());

        //wrapper
        //加载servlet并将其封装成wappers
        List<MappedWrapper> mappedWrappers = loadMappedWrappers(contextFile.getAbsolutePath());
        mappedContext.setMappedWrappers(mappedWrappers);

        return mappedContext;
    }

    private List<MappedWrapper> loadMappedWrappers(String contextPath) throws Exception {
        List<MappedWrapper> mappedWrappers = new ArrayList<>();

        //读取web.xml
        File webXmlFile = new File(contextPath + "/web.xml");
        FileInputStream fileInputStream = new FileInputStream(webXmlFile);
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(fileInputStream);
        Element rootElement = document.getRootElement();

        List<Element> servletElements = rootElement.selectNodes("//servlet");
        for(Element element : servletElements){
            //解析servlet标签
            //<servlet-name>helloServlet</servlet-name>
            Element servletNameEle = (Element) element.selectSingleNode("servlet-name");
            String servletName = servletNameEle.getStringValue();
            //<servlet-class>server.LagouServlet</servlet-class>
            Element servletClassEle = (Element) element.selectSingleNode("servlet-class");
            String servletClass = servletClassEle.getStringValue();

            //获取对应的servlet-mapping标签
            Element servletMappingEle = (Element)rootElement.selectSingleNode("/web-app/servlet-mapping[servlet-name='" + servletName + "']");
            Element urlPatternEle = (Element) servletMappingEle.selectSingleNode("url-pattern");
            String urlPattern = urlPatternEle.getStringValue();

            //创建MappedWrapper
            MappedWrapper mappedWrapper = new MappedWrapper();
            mappedWrapper.setServletUriPattern(urlPattern);

            //加载ServletClass类，并创建其对象
            ClassLoader classLoader = new WebApplicationClassloader(contextPath);
            Class<?> servletClazz = classLoader.loadClass(servletClass);
            if(servletClazz != null){
                //创建HttpServlet对象
                mappedWrapper.setServlet((HttpServlet) servletClazz.getDeclaredConstructor().newInstance());
            }

            mappedWrappers.add(mappedWrapper);
        }

        return mappedWrappers;
    }


    public List<Connector> getConnectors() {
        return connectors;
    }

    public void setConnectors(List<Connector> connectors) {
        this.connectors = connectors;
    }

    public Mapper getMapper() {
        return mapper;
    }

    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    public List<Host> getHosts() {
        return hosts;
    }

    public void setHosts(List<Host> hosts) {
        this.hosts = hosts;
    }
}
