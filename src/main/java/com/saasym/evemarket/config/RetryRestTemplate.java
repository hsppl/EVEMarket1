package com.saasym.evemarket.config;

import io.netty.channel.ConnectTimeoutException;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HttpContext;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.ConnectException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class RetryRestTemplate {
    /**
     * 失败重试默认3次，重试间隔时间=次数*1000 ms. 默认毫秒
     * @return
     */
    public static RestTemplate build(){
        return build(3,10000);
    }

    /**
     * @param retryTimes  重试次数
     * @param retryIntervalTime 每次重试间隔时间=重试次数*retryIntervalTime
     * @return
     */
    public static RestTemplate build(int retryTimes, long retryIntervalTime){
        RestTemplate restTemplate = new RestTemplate();
        //用UTF-8 StringHttpMessageConverter替换默认StringHttpMessageConverter
        List<HttpMessageConverter<?>> newMessageConverters = new ArrayList<>();
        for(HttpMessageConverter<?> converter : restTemplate.getMessageConverters()){
            if(converter instanceof StringHttpMessageConverter){
                // 默认的是ios 8859-1
                StringHttpMessageConverter messageConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
                newMessageConverters.add(messageConverter);
            }else {
                newMessageConverters.add(converter);
            }
        }
        restTemplate.setMessageConverters(newMessageConverters);

        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        // 只有io异常才会触发重试
        HttpRequestRetryHandler handler = new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException exception, int curRetryCount, HttpContext context) {
                // curRetryCount 每一次都会递增，从1开始
                if (curRetryCount > retryTimes) {
                    return false;
                }
                try {
                    //重试延迟
                    Thread.sleep(curRetryCount*retryIntervalTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (exception instanceof ConnectTimeoutException
                        || exception instanceof NoHttpResponseException || exception instanceof ConnectException) {
                    return true;
                }

                HttpClientContext clientContext = HttpClientContext.adapt(context);
                org.apache.http.HttpRequest request = clientContext.getRequest();
                boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
                if (idempotent) {
                    // 如果请求被认为是幂等的，那么就重试。即重复执行不影响程序其他效果的
                    return true;
                }
                return false;
            }
        };
        httpClientBuilder.setRetryHandler(handler).setMaxConnTotal(400);

        // httpClient连接配置，底层是配置RequestConfig
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClientBuilder.build());
        // 连接超时
        clientHttpRequestFactory.setConnectTimeout(30000);
        // 数据读取超时时间，即SocketTimeout
        clientHttpRequestFactory.setReadTimeout(30000);
        // 连接不够用的等待时间，不宜过长，必须设置，比如连接不够用时，时间过长将是灾难性的
        clientHttpRequestFactory.setConnectionRequestTimeout(10000);
        // 缓冲请求数据，默认值是true。通过POST或者PUT大量发送数据时，建议将此属性更改为false，以免耗尽内存。
        clientHttpRequestFactory.setBufferRequestBody(false);
        restTemplate.setRequestFactory(clientHttpRequestFactory);
        return restTemplate;
    }
}