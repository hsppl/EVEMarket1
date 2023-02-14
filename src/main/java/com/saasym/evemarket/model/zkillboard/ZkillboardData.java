package com.saasym.evemarket.model.zkillboard;

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
public class ZkillboardData implements Serializable {
    @Serial
    private static final long serialVersionUID = -27282277129831234L;

    private int killmail_id;

    private ZkillboardDataZKB zkb;

    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ZkillboardDataZKB implements Serializable{
        @Serial
        private static final long serialVersionUID = -272378129831234L;

        private String locationID;
        private String hash;
        private double fittedValue;
        private double droppedValue;
        private double destroyedValue;
        private double totalValue;
        private int points;
        private boolean npc;
        private boolean solo;
        private boolean awox;
    }
}

