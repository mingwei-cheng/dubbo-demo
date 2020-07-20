package cn.cheng.dubbo.provider.protocol.http;

import cn.cheng.dubbo.common.pojo.URL;
import cn.cheng.dubbo.provider.common.ServerProtocol;

/**
 * 启动http服务
 *
 * @author Cheng Mingwei
 * @create 2020-07-17 16:28
 **/
public class HttpProtocol implements ServerProtocol {
    @Override
    public void start(URL url) {
        HttpServer httpServer = new HttpServer();
        //启动http服务端，并阻塞
        httpServer.start(url.getHostName(), url.getPort());
    }
}
