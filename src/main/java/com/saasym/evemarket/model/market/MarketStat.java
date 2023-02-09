package com.saasym.evemarket.model.market;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class MarketStat implements Serializable{

    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 8842889366181968833L;

    private Buy buy;
    private Sell sell;

//    private List<MarketStatInfo> mList;
//
//    @Data
//    @NoArgsConstructor
//    private static class MarketStatInfo implements Serializable{
//        /**
//         *
//         */
//        @Serial
//        private static final long serialVersionUID = -9195966617524044214L;
//
//        private Buy buy;
//        private Sell sell;
//    }

    @Data
    @NoArgsConstructor
    public static class Buy implements Serializable{
        /**
         *
         */
        @Serial
        private static final long serialVersionUID = 5661807327889007880L;

        private ForQuery forQuery;
        private BigDecimal volume;
        private BigDecimal  wavg;
        private BigDecimal  avg;
        private BigDecimal  min;
        private BigDecimal  max;
        private BigDecimal  variance;
        private BigDecimal  stdDev;
        private BigDecimal  median;
        private BigDecimal  fivePercent;
        private boolean highToLow;
        private BigDecimal  generated;
    }

    @Data
    @NoArgsConstructor
    public static class ForQuery implements Serializable {
        /**
         *
         */
        @Serial
        private static final long serialVersionUID = -8945133111605689269L;

        private boolean bid;
        private List<Integer> types;
        private List<Integer> regions;
        private List<Integer> systems;
        private Integer hours;
        private Integer minq;
    }

    @Data
    @NoArgsConstructor
    public static class Sell implements Serializable{
        /**
         *
         */
        @Serial
        private static final long serialVersionUID = -2748904806072656225L;

        private ForQuery forQuery;
        private BigDecimal  volume;
        private BigDecimal  wavg;
        private BigDecimal  avg;
        private BigDecimal  min;
        private BigDecimal  max;
        private BigDecimal  variance;
        private BigDecimal  stdDev;
        private BigDecimal  median;
        private BigDecimal  fivePercent;
        private boolean highToLow;
        private BigDecimal  generated;
    }
}
