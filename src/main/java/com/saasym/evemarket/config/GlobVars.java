package com.saasym.evemarket.config;

import com.saasym.evemarket.model.esi.SimpleItemInfo;
import com.saasym.evemarket.model.esi.UniverseTypesDetail;

import java.util.List;

public final class GlobVars {
    private GlobVars(){}

    public static List<SimpleItemInfo> universeTypesDetailList;

    public static int lastKBId;
    public static int lastKMId;
}