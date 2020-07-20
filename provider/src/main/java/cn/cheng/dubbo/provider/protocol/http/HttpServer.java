package cn.cheng.dubbo.provider.protocol.http;

import org.apache.catalina.*;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardEngine;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.startup.Tomcat;

/**
 * 手写Tomcat启动
 *
 * @author Cheng Mingwei
 * @create 2020-07-17 14:11
 **/
public class HttpServer {
    public void start(String hostName, Integer port) {
        //实例化Tomcat
        Tomcat tomcat = new Tomcat();
        Server server = tomcat.getServer();
        //取出Service
        Service service = server.findService("Tomcat");

        //设置端口号
        Connector connector = new Connector();
        connector.setPort(port);

        //设置hostname
        Engine engine = new StandardEngine();
        engine.setDefaultHost(hostName);

        //设置名字
        Host host = new StandardHost();
        host.setName(hostName);

        //设置contextPath
        String contextPath = "";
        Context context = new StandardContext();
        context.setPath(contextPath);
        context.addLifecycleListener(new Tomcat.FixContextListener());

        //添加
        host.addChild(context);
        engine.addChild(host);

        service.setContainer(engine);
        service.addConnector(connector);

        //添加自己手写的DispatcherServlet到tomcat
        tomcat.addServlet(contextPath,"dispatcher",new DispatcherServlet());
        //指定dispatcher要处理哪些请求
        context.addServletMappingDecoded("/*","dispatcher");

        try {
            tomcat.start();
            System.out.println("-------- Tomcat已启动 -------- http://" + hostName + ":" + port);
            tomcat.getServer().await();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }

    }
}
