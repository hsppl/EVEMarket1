package com.saasym.evemarket.model.esi;

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
public class UniverseTypesDetail implements Serializable {

    /**
     *
     */
    @Serial
    private static final long serialVersionUID = -3168192027089043907L;

    private Float capacity;
    private String description;
    //@JsonProperty("dogma_attributes")
    private List<DogmaAttribute> dogma_attributes;
    //@JsonProperty("dogma_effects")
    private List<DogmaEffect> dogma_effects;
    private Integer graphic_id;
    private Integer group_id;
    private Integer icon_id;
    private Integer market_group_id;
    private Float mass;
    private String name;
    private Float packaged_volume;
    private Integer portion_size;
    private boolean published;
    private Float radius;
    private Integer type_id;
    private Float volume;


    @Data
    @NoArgsConstructor
    public static class DogmaEffect implements Serializable{
        /**
         *
         */
        @Serial
        private static final long serialVersionUID = -2879001447420175624L;

        private Integer effect_id;
        private boolean is_default;
    }

    @Data
    @NoArgsConstructor
    public static class DogmaAttribute implements Serializable{
        /**
         *
         */
        @Serial
        private static final long serialVersionUID = -7969762103180030453L;

        private Integer attribute_id;
        private float value;
    }
}