package cn.cheng.dubbo.provider.common;

import cn.cheng.dubbo.provider.protocol.http.HttpProtocol;
import cn.cheng.dubbo.provider.protocol.netty.NettyProtocol;

/**
 * 服务调度工厂
 * 选择netty或http模式启动
 *
 * @author Cheng Mingwei
 * @create 2020-07-17 16:32
 **/
public class ProtocolFactory {

    public static ServerProtocol protocol() {
        String name = System.getProperty("type");
        if (name == null || "".equals(name)) {
            name = "http";
        }
        switch (name) {
            case "netty":
                System.out.println("-------- netty模式已启动 --------");
                return new NettyProtocol();
            case "http":
            default:
                System.out.println("-------- http模式已启动 --------");
                return new HttpProtocol();
        }
    }
}
