package com.saasym.evemarket.model.miraiapi;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupMessageModel implements Serializable {
    @Serial
    private static final long serialVersionUID = -3168192689043907L;

    private String sessionKey;
    private Integer target;
    private List<MessageChain> messageChain;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MessageChain implements Serializable{
        @Serial
        private static final long serialVersionUID = -31681291743907L;

        private String type;
        private String text;
        private String url;
        private String path;
        private String base64;
    }
}
