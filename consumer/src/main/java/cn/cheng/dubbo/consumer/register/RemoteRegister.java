package cn.cheng.dubbo.consumer.register;

import cn.cheng.dubbo.common.pojo.URL;
import cn.cheng.dubbo.consumer.utils.JedisUtils;
import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 远程注册
 *
 * @author Cheng Mingwei
 * @create 2020-07-17 16:06
 **/
public class RemoteRegister {
    private static Map<String, List<URL>> remoteMap = new HashMap<>();

    public static List<URL> get(String interfaceName) {
        read(interfaceName);
        return remoteMap.get(interfaceName);
    }

    /**
     * 通过Redis获取远程注册的地址
     *
     * @param interfaceName
     */
    private static void read(String interfaceName) {
        List<String> list = JedisUtils.getList("dubbo-remote-interface-" + interfaceName, 0, -1);
        List<URL> urls = new ArrayList<>();
        list.forEach(s -> {
            urls.add(JSONObject.parseObject(s, URL.class));
        });
        remoteMap.put(interfaceName, urls);
    }
}
