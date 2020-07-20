package cn.cheng.dubbo.consumer.common;

import cn.cheng.dubbo.common.pojo.URL;

import java.util.List;
import java.util.Random;

/**
 * 负载均衡
 * 可自定义负载均衡方法
 *
 * @author Cheng Mingwei
 * @create 2020-07-17 16:11
 **/
public class LoadBalance {

    /**
     * 随机调用哪个url
     *
     * @param list
     * @return
     */
    public static URL random(List<URL> list) {
        Random random = new Random();
        int i = random.nextInt(list.size());
        return list.get(i);
    }
}
