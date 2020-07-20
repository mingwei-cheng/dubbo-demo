package cn.cheng.dubbo.provider.common;

import cn.cheng.dubbo.common.pojo.URL;
import cn.cheng.dubbo.common.service.HelloService;
import cn.cheng.dubbo.common.service.impl.HelloServiceImpl;
import cn.cheng.dubbo.provider.register.LocalRegister;
import cn.cheng.dubbo.provider.register.RemoteRegister;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author Cheng Mingwei
 * @create 2020-07-17 13:59
 **/
@Component
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        remoteRegister();
        localRegister();
        startTomcat();
    }

    /**
     * 远程服务注册
     */
    private void remoteRegister() {
        RemoteRegister.register(HelloService.class.getName(), new URL("localhost", 8080));
    }

    /**
     * 注册本地服务
     */
    private void localRegister() {
        LocalRegister.register(HelloService.class.getName(), HelloServiceImpl.class);
    }

    /**
     * 启动Tomcat
     */
    private void startTomcat() {
        ServerProtocol protocol = ProtocolFactory.protocol();
        protocol.start(new URL("localhost", 8080));
    }
}
