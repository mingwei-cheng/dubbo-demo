package cn.cheng.dubbo.provider.protocol.netty;

import cn.cheng.dubbo.common.pojo.URL;
import cn.cheng.dubbo.provider.common.ServerProtocol;

/**
 * 启动netty服务
 *
 * @author Cheng Mingwei
 * @create 2020-07-17 16:35
 **/
public class NettyProtocol implements ServerProtocol {
    @Override
    public void start(URL url) {
        NettyServer nettyServer = new NettyServer();
        //启动netty服务端，并阻塞
        nettyServer.start(url.getHostName(), url.getPort());
    }
}
