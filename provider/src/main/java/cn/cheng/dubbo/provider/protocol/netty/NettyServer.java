package cn.cheng.dubbo.provider.protocol.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * Netty服务端
 *
 * @author Cheng Mingwei
 * @create 2020-07-17 14:11
 **/
public class NettyServer {
    public void start(String hostName, Integer port) {
        try {
            //bossGroup就是parentGroup，是负责处理TCP/IP连接的
            EventLoopGroup bossGroup = new NioEventLoopGroup();
            //workerGroup就是childGroup,是负责处理Channel(通道)的I/O事件
            EventLoopGroup workerGroup = new NioEventLoopGroup();

            ServerBootstrap sb = new ServerBootstrap();
            sb.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    //初始化服务端可连接队列,指定了队列的大小128
                    .option(ChannelOption.SO_BACKLOG, 128)
                    //保持长连接
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    // 绑定客户端连接时候触发操作
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel sh) throws Exception {
                            System.out.println("-------- 有新连接 --------");
                            sh.pipeline()
                                    //对String类型的数据进行编码
                                    .addLast(new StringEncoder())
                                    //对String类型的数据进行解码
                                    .addLast(new StringDecoder())
                                    //使用NettyServerHandler类来处理接收到的消息
                                    .addLast(new NettyServerHandler());
                        }
                    });
            //绑定监听端口，调用sync同步阻塞方法等待绑定操作完
            ChannelFuture future = sb.bind(hostName, port).sync();

            if (future.isSuccess()) {
                System.out.println("-------- netty服务启动成功 --------");
            } else {
                System.out.println("-------- netty服务启动失败 --------");
                future.cause().printStackTrace();
                //关闭线程组
                bossGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();
            }

            //成功绑定到端口之后,给channel增加一个 管道关闭的监听器并同步阻塞,直到channel关闭,线程才会往下执行,结束进程。
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
