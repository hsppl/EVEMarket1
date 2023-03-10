package com.saasym.evemarket.controller;

import com.saasym.evemarket.model.ResponseTemplate;
import com.saasym.evemarket.service.QueryService;
import com.saasym.evemarket.service.UniverseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/test/")
public class TestController {
    private final UniverseService universeService;
    private final QueryService queryService;

    public TestController(UniverseService uService, QueryService queryService) {
        this.universeService = uService;
        this.queryService = queryService;
    }

    @GetMapping(value = "getUniverseTypes",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseTemplate getUniverseTypes(@RequestParam int pageIndex) {
        return ResponseTemplate.builder()
                .code(0)
                .success(true)
                .message("ok")
                .data(universeService.getEsiUniverseTypes(pageIndex))
                .build();
    }

    @GetMapping(value = "getEsiUniverseTypeDetails",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseTemplate getEsiUniverseTypeDetails(@RequestParam int id) {
        return ResponseTemplate.builder()
                .code(0)
                .success(true)
                .message("ok")
                .data(universeService.getSimpleItemInfo(id))
                .build();
    }

    @GetMapping(value = "RedisToMem",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseTemplate RedisToMem(){
        return ResponseTemplate.builder()
                .code(0)
                .success(true)
                .message("ok")
                .data(queryService.RedisToMem())
                .build();
    }
}
