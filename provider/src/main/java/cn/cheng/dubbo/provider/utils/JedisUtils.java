package cn.cheng.dubbo.provider.utils;

import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * jedis工具类
 *
 * @author Cheng Mingwei
 * @create 2020-07-17 16:51
 **/
public class JedisUtils {
    static Jedis jedis;

    static {
        jedis = new Jedis("192.168.0.128", 6379);
        jedis.auth("123456");
        //清除上一次存的
        Set<String> lrange = jedis.keys("dubbo-*");
        lrange.forEach(s -> {
            jedis.del(s);
        });
        System.out.println("-------- Redis已启动 -------- ");
    }

    public static void saveList(String key, String value) {
        jedis.lpush(key, value);
    }
}
