package com.saasym.evemarket.service;

import com.saasym.evemarket.model.shorturl.ShortUrlBack;
import com.saasym.evemarket.model.shorturl.ShortUrlModel;
import com.saasym.evemarket.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class ShortUrlService {
    private final static String API_KEY="Token bapMTO6VKpHXAzZXM1cm";
    private final static String API_URL="https://www.urlc.cn/api/url/add";

    public String getUrl(String rawUrl) throws UnsupportedEncodingException {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(API_URL);
        httpPost.setHeader("Content-type","application/json; charset=UTF-8");
        httpPost.setHeader("Authorization",API_KEY);
        StringEntity entity=new StringEntity(JsonUtils.toJSONString(ShortUrlModel.builder().url(rawUrl).build()), StandardCharsets.UTF_8);
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);

        String temp="";
        CloseableHttpResponse httpResponse;
        try{
            httpResponse = httpClient.execute(httpPost);
            temp = JsonUtils.toJavaObject(EntityUtils.toString(httpResponse.getEntity(),"UTF-8"),ShortUrlBack.class).getShort();
        }catch (Exception e){

        }

        return temp;

       /* RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Authorization",API_KEY);

        HttpEntity<String> request = new HttpEntity<String>(JsonUtils.toJSONString(ShortUrlModel.builder().url(rawUrl).build()), headers);
        ShortUrlBack u = restTemplate.postForEntity("https://www.urlc.cn/api/url/add", request, ShortUrlBack.class).getBody();

        return u.getShort();*/
    }
}
