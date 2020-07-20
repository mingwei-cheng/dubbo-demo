package cn.cheng.dubbo.provider.protocol.http;

import cn.cheng.dubbo.common.pojo.Invocation;
import cn.cheng.dubbo.provider.register.LocalRegister;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 处理Http连接后信息
 *
 * @author Cheng Mingwei
 * @create 2020-07-17 14:24
 **/
public class HttpServerHandler {


    /**
     * 处理http收到消息后
     *
     * @param req
     * @param resp
     */
    public void handle(HttpServletRequest req, HttpServletResponse resp) {
        try {
            //从输出流中接收数据，转换成Invocation对象
            Invocation invocation = JSONObject.parseObject(req.getInputStream(), Invocation.class);
            //从对象中获取接口名
            String interfaceName = invocation.getInterfaceName();
            //从本地注册器中，获取出相关的Class
            Class implClass = LocalRegister.getRegister(interfaceName);
            //通过方法名和参数，从Class中，获取出方法
            Method method = implClass.getMethod(invocation.getMethodName(), invocation.getParamTypes());
            //通过invoke调用方法，获取返回值
            String result = (String) method.invoke(implClass.newInstance(), invocation.getParam());

            System.out.println(result);
            //返回给客户端
            IOUtils.write(result, resp.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
