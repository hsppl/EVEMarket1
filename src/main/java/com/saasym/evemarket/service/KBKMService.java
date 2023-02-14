package com.saasym.evemarket.service;

import com.saasym.evemarket.model.zkillboard.ZkillboardData;
import com.saasym.evemarket.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class KBKMService {
    private final static String KB_API="https://zkillboard.com/api/kills/corporationID/98568863/pastSeconds/3600/";
    private final static String KM_API="https://zkillboard.com/api/kills/corporationID/98568863/pastSeconds/3600/";


    public ZkillboardData GetCorpLastKB(){
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;

        try{
            httpClient = HttpClients.createDefault();
            HttpGet get = new HttpGet(KB_API);
            response = httpClient.execute(get);

            HttpEntity entity = response.getEntity();

            List<ZkillboardData> list = JsonUtils.toJavaObjectList(EntityUtils.toString(entity), ZkillboardData.class);

            httpClient.close();
            if(list == null || (long) list.size() == 0) return null;
            return list.stream().max(Comparator.comparing(ZkillboardData::getKillmail_id)).get();

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public ZkillboardData GetCorpLastKM(){
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;

        try{
            httpClient = HttpClients.createDefault();
            HttpGet get = new HttpGet(KM_API);
            response = httpClient.execute(get);

            HttpEntity entity = response.getEntity();

            List<ZkillboardData> list = JsonUtils.toJavaObjectList(EntityUtils.toString(entity), ZkillboardData.class);

            httpClient.close();
            if(list == null || (long) list.size() == 0) return null;
            return list.stream().max(Comparator.comparing(ZkillboardData::getKillmail_id)).get();

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
