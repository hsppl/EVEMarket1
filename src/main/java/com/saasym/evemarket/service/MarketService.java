package com.saasym.evemarket.service;

import com.saasym.evemarket.model.market.MarketStat;
import com.saasym.evemarket.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
public class MarketService {
    private final static int CODE_JITA = 30000142;		//JITA
    private final static int CODE_JITA4 = 60003760;		//JITA-IV
    private final static int CODE_Forge = 10000002;		//伏尔戈
    private final static int CODE_Perimeter = 30000144;	//皮尔米特

    public List<MarketStat> getItemMarketInfo(int regionID, int systemID, int itemID) {
        String market_url="https://api.evemarketer.com/ec/marketstat/json?typeid="+itemID+"&regionlimit="+regionID+"&usesystem="+systemID;

        RestTemplate restTemplate = new RestTemplate();

        Object sessionEntity = restTemplate.postForObject(market_url, null,String.class);

        //log.info(sessionEntity.toString());
        return JsonUtils.toJavaObjectList(sessionEntity.toString(), MarketStat.class);

    }
}
