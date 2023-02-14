package com.saasym.evemarket.controller;

import com.saasym.evemarket.service.ShortUrlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@Slf4j
@RestController
@RequestMapping(value = "/api/short/")
public class ShortUrlController {
    private final ShortUrlService shortUrlService;

    public ShortUrlController(ShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }

    @GetMapping(value = "getUrl",produces = MediaType.APPLICATION_JSON_VALUE)
    public String getUrl(@RequestParam String rawUrl) throws UnsupportedEncodingException {
        return shortUrlService.getUrl(rawUrl);
    }
}
