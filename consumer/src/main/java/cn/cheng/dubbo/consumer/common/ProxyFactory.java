package cn.cheng.dubbo.consumer.common;

import cn.cheng.dubbo.common.pojo.Invocation;
import cn.cheng.dubbo.common.pojo.URL;
import cn.cheng.dubbo.common.service.HelloService;
import cn.cheng.dubbo.consumer.protocol.http.HttpClient;
import cn.cheng.dubbo.consumer.protocol.http.HttpProtocol;
import cn.cheng.dubbo.consumer.register.RemoteRegister;

import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @author Cheng Mingwei
 * @create 2020-07-17 15:59
 **/
public class ProxyFactory {

    /**
     * 动态代理
     *
     * @param interfaceClass
     * @param <T>
     * @return
     */
    public static <T> T getProxy(Class interfaceClass) {
        //动态代理
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, (proxy, method, args) -> {
            //通过静态工厂获取当前应该用什么模式启动
            ClientProtocol protocol = ProtocolFactory.protocol();
            //实例化一个Invocation对象
            Invocation invocation = new Invocation(interfaceClass.getName(), method.getName(), method.getParameterTypes(), args);
            //获取远程地址列表
            List<URL> urls = RemoteRegister.get(interfaceClass.getName());
            //随机负载均衡
            URL url = LoadBalance.random(urls);
            //调用远程，返回结果
            String result = protocol.send(url, invocation);
            return result;
        });

    }
}
