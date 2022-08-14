package com.saasym.evemarket.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTemplate implements Serializable {
    @Serial
    private static final long serialVersionUID = -2728227712983503304L;
    private Boolean success;
    private Integer code;
    private String message;
    private Object data;
}
