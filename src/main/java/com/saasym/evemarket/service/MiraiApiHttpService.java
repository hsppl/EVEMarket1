package com.saasym.evemarket.service;

import com.saasym.evemarket.config.GlobVars;
import com.saasym.evemarket.model.miraiapi.GroupMessageModel;
import com.saasym.evemarket.model.miraiapi.MiraiApiBack;
import com.saasym.evemarket.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class MiraiApiHttpService {

    private final static String MIRAI_API="http://127.0.0.1:18080/sendGroupMessage";

    public boolean SendCorpKBMessage(){
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;

        try{
            List<GroupMessageModel.MessageChain> messageChains=new ArrayList<>();
            messageChains.add(GroupMessageModel.MessageChain.builder().url("https://files.saasym.com/kzimages/lastKB.png").build());
            messageChains.add(GroupMessageModel.MessageChain.builder().text("发现一枚KB，MMIC损失惨重.\n").build());
            messageChains.add(GroupMessageModel.MessageChain.builder().text("https://zkillboard.com/kill/"+ GlobVars.lastKBId+"/").build());

            GroupMessageModel groupMessageModel = GroupMessageModel.builder()
                    .target(335143536)
                    .messageChain(messageChains)
                    .build();

            httpClient = HttpClients.createDefault();
            HttpPost post = new HttpPost(MIRAI_API);
            post.addHeader("Content-Type","application/json");
            post.setEntity(new StringEntity(JsonUtils.toJSONString(groupMessageModel)));
            response= httpClient.execute(post);

            MiraiApiBack miraiApiBack = JsonUtils.toJavaObject(EntityUtils.toString(response.getEntity()), MiraiApiBack.class);

            return miraiApiBack != null && miraiApiBack.getCode() == 0;

        }catch (Exception e){
            return false;
        }
    }

    public boolean SendCorpKMMessage(){
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;

        try{
            List<GroupMessageModel.MessageChain> messageChains=new ArrayList<>();
            messageChains.add(GroupMessageModel.MessageChain.builder().url("https://files.saasym.com/kzimages/lastKM.png").build());
            messageChains.add(GroupMessageModel.MessageChain.builder().text("发现一枚KM，MMIC今天吃肉.\n").build());
            messageChains.add(GroupMessageModel.MessageChain.builder().text("https://zkillboard.com/kill/"+ GlobVars.lastKMId+"/").build());

            GroupMessageModel groupMessageModel = GroupMessageModel.builder()
                    .target(335143536)
                    .messageChain(messageChains)
                    .build();

            httpClient = HttpClients.createDefault();
            HttpPost post = new HttpPost(MIRAI_API);
            post.addHeader("Content-Type","application/json");
            post.setEntity(new StringEntity(JsonUtils.toJSONString(groupMessageModel)));
            response= httpClient.execute(post);

            MiraiApiBack miraiApiBack = JsonUtils.toJavaObject(EntityUtils.toString(response.getEntity()), MiraiApiBack.class);

            return miraiApiBack != null && miraiApiBack.getCode() == 0;

        }catch (Exception e){
            return false;
        }
    }
}
