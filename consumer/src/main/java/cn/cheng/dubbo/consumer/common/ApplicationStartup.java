package cn.cheng.dubbo.consumer.common;

import cn.cheng.dubbo.common.service.HelloService;
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
        //通过代理调用服务
        HelloService helloService = ProxyFactory.getProxy(HelloService.class);
        String result = helloService.hi("Cheng");

        System.out.println(result);
    }

}
