package cn.cheng.dubbo.consumer.protocol.netty;

import cn.cheng.dubbo.common.pojo.Invocation;
import com.alibaba.fastjson.JSONObject;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.concurrent.CompletableFuture;

/**
 * @author Cheng Mingwei
 * @create 2020-07-17 17:30
 **/
public class NettyClient {

    public Channel send(String hostname, Integer port, Invocation invocation, CompletableFuture<String> completableFuture) {
        try {

            final EventLoopGroup group = new NioEventLoopGroup();

            Bootstrap b = new Bootstrap();
            // 使用NioSocketChannel来作为连接用的channel类
            b.group(group).channel(NioSocketChannel.class)
                    // 绑定连接初始化器
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            System.out.println("正在连接中...");
                            ChannelPipeline pipeline = ch.pipeline();
                            //客户端处理类
                            pipeline
                                    .addLast(new StringDecoder())
                                    .addLast(new StringEncoder())
                                    .addLast(new SimpleChannelInboundHandler() {
                                        @Override
                                        protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
                                            completableFuture.complete(o.toString());
                                        }
                                    });

                        }
                    });
            //发起异步连接请求，绑定连接端口和host信息
            final ChannelFuture future = b.connect(hostname, port).sync();

            future.addListener((ChannelFutureListener) arg0 -> {
                if (future.isSuccess()) {
                    System.out.println("-------- 连接netty服务器成功 --------");
                    future.channel().writeAndFlush(JSONObject.toJSONString(invocation));
                } else {
                    System.out.println("-------- 连接netty服务器失败 -------");
                    future.cause().printStackTrace();
                    group.shutdownGracefully(); //关闭线程组
                }
            });
            return future.channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
