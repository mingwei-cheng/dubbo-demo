package cn.cheng.dubbo.consumer.protocol.http;


import cn.cheng.dubbo.common.pojo.Invocation;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Cheng Mingwei
 * @create 2020-07-17 15:02
 **/
public class HttpClient {
    public String send(String hostname, Integer port, Invocation invocation) {
        try {
            URL url = new URL("http", hostname, port, "/");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);

            OutputStream outputStream = httpURLConnection.getOutputStream();

            outputStream.write(JSONObject.toJSONBytes(invocation));
            outputStream.flush();
            outputStream.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            return IOUtils.toString(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
