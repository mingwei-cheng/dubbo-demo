package cn.cheng.dubbo.consumer.protocol.http;

import cn.cheng.dubbo.common.pojo.Invocation;
import cn.cheng.dubbo.common.pojo.URL;
import cn.cheng.dubbo.consumer.common.ClientProtocol;

/**
 * @author Cheng Mingwei
 * @create 2020-07-17 16:26
 **/
public class HttpProtocol implements ClientProtocol {

    @Override
    public String send(URL url, Invocation invocation) {
        HttpClient httpClient = new HttpClient();
        return httpClient.send(url.getHostName(), url.getPort(), invocation);
    }
}
