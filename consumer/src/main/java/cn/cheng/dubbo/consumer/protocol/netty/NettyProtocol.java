package cn.cheng.dubbo.consumer.protocol.netty;

import cn.cheng.dubbo.common.pojo.Invocation;
import cn.cheng.dubbo.common.pojo.URL;
import cn.cheng.dubbo.consumer.common.ClientProtocol;

import java.util.concurrent.CompletableFuture;


/**
 * @author Cheng Mingwei
 * @create 2020-07-17 16:34
 **/
public class NettyProtocol implements ClientProtocol {
    @Override
    public String send(URL url, Invocation invocation) {
        NettyClient nettyClient = new NettyClient();
        //异步返回结果
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        nettyClient.send(url.getHostName(), url.getPort(), invocation, completableFuture);
        try {
            return completableFuture.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "请求失败";
    }
}
