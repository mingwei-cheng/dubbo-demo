package cn.cheng.dubbo.provider.register;

import cn.cheng.dubbo.common.pojo.URL;
import cn.cheng.dubbo.provider.utils.JedisUtils;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 远程服务注册
 *
 * @author Cheng Mingwei
 * @create 2020-07-17 16:06
 **/
public class RemoteRegister {

    private static Map<String, List<URL>> remoteMap = new HashMap<>();

    /**
     * 注册
     *
     * @param interfaceName
     * @param url
     */
    public static void register(String interfaceName, URL url) {
        List<URL> urls = remoteMap.get(interfaceName);
        if (urls == null) {
            urls = new ArrayList<>();
        }
        urls.add(url);
        save(interfaceName, url);

        remoteMap.put(interfaceName, urls);
    }

    /**
     * 通过redis保存
     *
     * @param interfaceName
     * @param url
     */
    private static void save(String interfaceName, URL url) {
        JedisUtils.saveList("dubbo-remote-interface-" + interfaceName, JSONObject.toJSONString(url));
    }
}
