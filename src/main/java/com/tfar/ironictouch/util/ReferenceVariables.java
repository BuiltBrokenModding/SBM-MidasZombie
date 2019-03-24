package com.tfar.ironictouch.util;

import net.minecraft.item.Item;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class ReferenceVariables {
    public static ArrayList<String> uniqueTypesList = new ArrayList<>();

    public static int uniqueTypes = 1;
    public static final String MOD_ID = "ironictouch";
    public static final String NAME = "Ironic Touch";
    public static final String VERSION = "1.0";
    public static final String CLIENT_PROXY_CLASS = "com.tfar.ironictouch.proxy.ClientProxy";
    public static final String COMMON_PROXY_CLASS = "com.tfar.ironictouch.proxy.CommonProxy";
    public static HashMap<Pair<Item,String>,Item> conversionItems = new HashMap<>();

}
