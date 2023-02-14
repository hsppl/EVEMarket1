package com.saasym.evemarket.model.shorturl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortUrlBack implements Serializable {
    private int error;
    private String msg;
    private String Short;
}
