package cn.cheng.dubbo.provider.register;

import java.util.HashMap;
import java.util.Map;

/**
 * 本地服务注册
 *
 * @author Cheng Mingwei
 * @create 2020-07-17 16:09
 **/
public class LocalRegister {
    private static Map<String, Class> registerMap = new HashMap<>();

    public static Class getRegister(String interfaceName) {
        return registerMap.get(interfaceName);
    }

    public static void register(String name, Class helloServiceClass) {
        registerMap.put(name, helloServiceClass);
    }
}
