package com.saasym.evemarket.controller;

import com.saasym.evemarket.model.ResponseTemplate;
import com.saasym.evemarket.service.MarketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/market/")
public class MarketController {
    private final MarketService marketService;

    public MarketController(MarketService marketService) {
        this.marketService = marketService;
    }

    /**
     * 获取一个物品的价格信息
     * @param regionID  星域ID
     * @param systemID  星系ID
     * @param itemID    物品ID
     * @return {@link ResponseTemplate} ResponseTemplate实体类
     */
    @GetMapping(value = "getOneItemInfo",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseTemplate getOneItemInfo(@RequestParam int regionID,@RequestParam int systemID,@RequestParam int itemID){
        return ResponseTemplate.builder()
                .code(0)
                .message("ok")
                .success(true)
                .data(marketService.getItemMarketInfo(regionID, systemID, itemID))
                .build();
    }
}
