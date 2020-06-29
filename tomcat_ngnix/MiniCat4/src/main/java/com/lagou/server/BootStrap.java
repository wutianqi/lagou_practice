package com.lagou.server;

import com.lagou.server.component.Connector;
import com.lagou.server.component.Host;
import com.lagou.server.component.Server;
import com.lagou.server.component.Service;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * MiniCat4.0 模拟tomcat在webapps下部署应用并加载应用
 * @author wuqi
 * @date 2020-06-25 22:03
 */
public class BootStrap {
    /**
     * 初始化
     * @return
     * @throws DocumentException
     */
    private static Server init() throws DocumentException {
        //加载server.xml
        InputStream inputStream = BootStrap.class.getClassLoader().getResourceAsStream("server.xml");

        //读取server.xml
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(inputStream);
        Element rootElement = document.getRootElement();

        //创建Server
        Server server = new Server();

        //读取<service />标签
        List<Element> serviceElements  = rootElement.selectNodes("/server//service");
        List<Service> services = new ArrayList<>();
        for(Element serviceElement : serviceElements){
            //创建Service
            Service service = new Service();
            services.add(service);

            //读取<connector/>标签
            List<Element> connectorElements = serviceElement.selectNodes("//connector");
            List<Connector> connectors = new ArrayList<>();
            for(Element connectorElement : connectorElements){
                //创建Connector
                Connector connector = new Connector();
                connectors.add(connector);

                //端口
                String port = connectorElement.attributeValue("port");
                connector.setPort(port);
                //当前所在服务
                connector.setService(service);
            }

            //读取<host>标签
            List<Element> hostElements = serviceElement.selectNodes("//engine//host");
            List<Host> hosts = new ArrayList<>();
            for(Element hostElement : hostElements){
                //创建Host
                Host host = new Host();
                hosts.add(host);

                String name = hostElement.attributeValue("name");
                String appBase = hostElement.attributeValue("appBase");
                host.setName(name);
                host.setAppBase(appBase);
            }

            service.setConnectors(connectors);
            service.setHosts(hosts);
        }

        server.setServices(services);

        return server;
    }

    private static void start(Server server) throws Exception{
        //启动服务
        server.start();
    }

    public static void main(String[] args) {
        try {
            //初始化
            Server server = BootStrap.init();
            BootStrap.start(server);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
