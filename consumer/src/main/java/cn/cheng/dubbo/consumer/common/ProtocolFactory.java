package cn.cheng.dubbo.consumer.common;

import cn.cheng.dubbo.consumer.protocol.http.HttpProtocol;
import cn.cheng.dubbo.consumer.protocol.netty.NettyProtocol;

/**
 * @author Cheng Mingwei
 * @create 2020-07-17 16:32
 **/
public class ProtocolFactory {

    /**
     * 静态工厂
     * 通过启动参数决定使用哪个方式启动服务，netty或http
     *
     * @return
     */
    public static ClientProtocol protocol() {
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
