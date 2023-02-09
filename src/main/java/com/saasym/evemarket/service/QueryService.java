package com.saasym.evemarket.service;

import com.saasym.evemarket.config.GlobVars;
import com.saasym.evemarket.model.esi.SimpleItemInfo;
import com.saasym.evemarket.model.esi.UniverseTypesDetail;
import com.saasym.evemarket.utils.JsonUtils;
import com.saasym.evemarket.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class QueryService {
    @Resource
    private RedisUtil redisUtil;
    private final StringRedisTemplate exportRedis;

    public QueryService(StringRedisTemplate exportRedis) {
        this.exportRedis = exportRedis;
    }

    public boolean RedisToMem(){
        RedisSerializer<String> serializer = exportRedis.getStringSerializer();
        Set<String> keys = exportRedis.keys("*");

        log.info("Redis keys count:"+keys.stream().count());

        RedisCallback callback = connection -> {
            connection.openPipeline();
            for (String key : keys) {
                connection.get(serializer.serialize(key.toString()));
            }
            return null;
        };
        List<Object> result = exportRedis.executePipelined(callback);

        GlobVars.universeTypesDetailList=new ArrayList<>();

        for(Object object : result) {
            GlobVars.universeTypesDetailList.add(JsonUtils.toJavaObject(object,SimpleItemInfo.class));
        }


        return true;
    }

    public Integer GetItemId(String name){
        try{
            if (redisUtil.get(name)!=null){
                return (Integer) redisUtil.get(name);
            }else {
                return -1;
            }

        }catch (Exception e){
            return -1;
        }
    }
}
