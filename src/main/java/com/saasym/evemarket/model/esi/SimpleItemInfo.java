package com.saasym.evemarket.model.esi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimpleItemInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = -316819202708943217L;

    private String name;
    private Integer type_id;
}
