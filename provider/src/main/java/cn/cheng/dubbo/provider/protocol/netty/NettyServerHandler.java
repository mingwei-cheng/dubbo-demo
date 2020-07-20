package cn.cheng.dubbo.provider.protocol.netty;

import cn.cheng.dubbo.common.pojo.Invocation;
import cn.cheng.dubbo.provider.register.LocalRegister;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.lang.reflect.Method;

/**
 * 处理Netty连接后信息
 *
 * @author Cheng Mingwei
 * @create 2020-07-17 17:25
 **/
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 接收到数据后
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //解析接收的数据，转换成Invocation对象
        Invocation invocation = JSONObject.parseObject((String) msg, Invocation.class);
        //从对象中获取接口名
        String interfaceName = invocation.getInterfaceName();
        //从本地注册器中，获取出相关的Class
        Class implClass = LocalRegister.getRegister(interfaceName);
        //通过方法名和参数，从Class中，获取出方法
        Method method = implClass.getMethod(invocation.getMethodName(), invocation.getParamTypes());
        //通过invoke调用方法，获取返回值
        String result = (String) method.invoke(implClass.newInstance(), invocation.getParam());

        System.out.println(result);
        //发送给客户端
        ctx.write(result);
    }

    /**
     * 接收完成后，刷新
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

}