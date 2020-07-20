package cn.cheng.dubbo.common.service.impl;

import cn.cheng.dubbo.common.service.HelloService;

/**
 * @author Cheng Mingwei
 * @create 2020-07-17 10:12
 **/
public class HelloServiceImpl implements HelloService {
    @Override
    public String hi(String username) {
        return "hi:" + username;
    }
}
