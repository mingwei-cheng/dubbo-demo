package cn.cheng.dubbo.consumer.utils;

import redis.clients.jedis.Jedis;

import java.util.List;

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
        System.out.println("-------- Redis已启动 -------- ");
    }

    public static List<String> getList(String key, int start, int end) {
        return jedis.lrange(key, start, end);
    }
}
