package cn.cheng.dubbo.provider.common;

import cn.cheng.dubbo.common.pojo.Invocation;
import cn.cheng.dubbo.common.pojo.URL;

public interface ServerProtocol {

    void start(URL url);

}
