package cn.cheng.dubbo.consumer.common;

import cn.cheng.dubbo.common.pojo.Invocation;
import cn.cheng.dubbo.common.pojo.URL;

public interface ClientProtocol {

    String send(URL url, Invocation invocation);

}
