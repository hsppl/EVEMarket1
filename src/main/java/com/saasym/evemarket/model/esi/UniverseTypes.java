package com.saasym.evemarket.model.esi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UniverseTypes implements Serializable {

    /**
     * serialVersionUID
     */
    @Serial
    private static final long serialVersionUID = -5551219731504832756L;

    /**
     * UniverseType
     */
    private List<Integer> id;

}
