package com.saasym.evemarket.controller;

import com.saasym.evemarket.model.ResponseTemplate;
import com.saasym.evemarket.model.market.MarketStat;
import com.saasym.evemarket.service.MarketService;
import com.saasym.evemarket.service.QueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/market/")
public class MarketController {
    private final MarketService marketService;
    private final QueryService queryService;
    public MarketController(MarketService marketService, QueryService queryService) {
        this.marketService = marketService;
        this.queryService = queryService;
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

    @GetMapping(value = "GetOneFromJita",produces = MediaType.APPLICATION_JSON_VALUE)
    public String GetOneFromJita(@RequestParam String itemName){
        int itemID=queryService.GetItemId(itemName);
        if (itemID == -1){
            /*return ResponseTemplate.builder()
                    .code(-1)
                    .message("没有找到该物品的ID,请刷新缓存")
                    .success(false)
                    .build();*/
            return "没有找到该物品的ID,请刷新缓存";
        }
        List<MarketStat> list = marketService.getItemMarketInfo(100000021,30000142,itemID);
        DecimalFormat decimalFormat = new DecimalFormat(",###.00 ISK");
        StringBuilder stringBuilder = new StringBuilder();

        for(MarketStat m : list){
            stringBuilder.append("--------\n");
            stringBuilder.append(itemName).append("\n");
            stringBuilder.append("收单-最高:").append(decimalFormat.format(m.getBuy().getMax())).append("\n");
            stringBuilder.append("收单-最低:").append(decimalFormat.format(m.getBuy().getMin())).append("\n");
            stringBuilder.append("收单-平均:").append(decimalFormat.format(m.getBuy().getAvg())).append("\n");
            stringBuilder.append("卖单-最高:").append(decimalFormat.format(m.getSell().getMax())).append("\n");
            stringBuilder.append("卖单-最低:").append(decimalFormat.format(m.getSell().getMin())).append("\n");
            stringBuilder.append("卖单-平均:").append(decimalFormat.format(m.getSell().getAvg())).append("\n");
        }
        return stringBuilder.toString();
        /*return ResponseTemplate.builder()
                .code(0)
                .message("ok")
                .success(true)
                .data(list)
                .build();*/
    }
}
