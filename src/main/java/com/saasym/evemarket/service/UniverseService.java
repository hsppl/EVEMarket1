package com.saasym.evemarket.service;

import com.saasym.evemarket.config.RetryRestTemplate;
import com.saasym.evemarket.model.esi.SimpleItemInfo;
import com.saasym.evemarket.model.esi.UniverseTypes;
import com.saasym.evemarket.model.esi.UniverseTypesDetail;
import com.saasym.evemarket.utils.JsonUtils;
import com.saasym.evemarket.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class UniverseService {
    @Resource
    private RedisUtil redisUtil;
    public Object getSessionEntity(int id,int value) {
        String esi_url =null;
        RestTemplate restTemplate = RetryRestTemplate.build();
        //RestTemplate restTemplate = new RestTemplate();
        switch (id) {
            case 1 -> {
                esi_url = "https://esi.evetech.net/latest/universe/types/?datasource=tranquility&page=" + value;
                break;
            }
            case 2 -> {
                esi_url = "https://esi.evetech.net/latest/universe/types/" + value + "/?datasource=tranquility&language=zh";
                break;
            }
            default -> {
                throw new IllegalArgumentException("Unexpected value: " + id);
            }
        }
        return restTemplate.getForObject(esi_url,String.class);
    }

    /**
     * 获取ESI中ID列表
     * @param pageIndex 页码数,需要大于等于1
     * @return {@link UniverseTypes} UniverseTypes实体类
     */
    public UniverseTypes getEsiUniverseTypes(int pageIndex) {
        try {
            Object sessionEntity = getSessionEntity(1, pageIndex);

            List<Object> list = JsonUtils.toList(sessionEntity.toString());
            UniverseTypes universeTypes = UniverseTypes.builder().id((List<Integer>)(List)list).build();

            String redisKey = "ESIUniverseTypes_"+pageIndex;
            if (redisUtil.hasKey(redisKey)){
                if (redisUtil.get(redisKey)!=null && !redisUtil.get(redisKey).equals(universeTypes)){
                    redisUtil.set("ESIUniverseTypes_"+pageIndex,JsonUtils.toJSONString(universeTypes),-1);
                }
            }else {
                redisUtil.set("ESIUniverseTypes_"+pageIndex,JsonUtils.toJSONString(universeTypes),-1);
            }
            return JsonUtils.toJavaObject(redisUtil.get(redisKey),UniverseTypes.class);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(),e);
            return null;
        }
    }

    /**
     * 获取ESI中物品信息
     * @param id 物品ID编号
     * @return {@link String}ItemName
     */
    public String getSimpleItemInfo(int id) {
        try {
            Object sessionEntity = getSessionEntity(2, id);

            UniverseTypesDetail universeTypesDetail = JsonUtils.toJavaObject(sessionEntity.toString(), UniverseTypesDetail.class);
            SimpleItemInfo simpleItemInfo = SimpleItemInfo.builder().name(universeTypesDetail.getName()).type_id(universeTypesDetail.getType_id()).build();

            String redisKey = simpleItemInfo.getName();//"SimpleItemInfo_"+id;
            if (redisUtil.hasKey(redisKey)){
                if (redisUtil.get(redisKey)!=null && !redisUtil.get(redisKey).equals(simpleItemInfo.getType_id())){
                    redisUtil.set(redisKey,simpleItemInfo.getType_id(),-1);
                }
            }else {
                redisUtil.set(redisKey,simpleItemInfo.getType_id(),-1);
            }

            //return JsonUtils.toJavaObject(redisUtil.get(redisKey),SimpleItemInfo.class);
            return redisUtil.get(redisKey).toString();

        } catch (Exception e) {
            log.error(e.getLocalizedMessage(),e);
            return null;
        }
    }
}
