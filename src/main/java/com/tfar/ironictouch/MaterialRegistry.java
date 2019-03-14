package com.tfar.ironictouch;

import net.minecraft.init.Items;

import static com.tfar.ironictouch.util.ReferenceVariables.conversionItems;

public class MaterialRegistry {

    public static void setup(){
        conversionItems.put(Items.GOLDEN_HELMET,"gold");
        conversionItems.put(Items.GOLDEN_CHESTPLATE,"gold");
        conversionItems.put(Items.GOLDEN_LEGGINGS,"gold");
        conversionItems.put(Items.GOLDEN_BOOTS,"gold");
        conversionItems.put(Items.GOLDEN_SWORD,"gold");
        conversionItems.put(Items.GOLDEN_AXE,"gold");
        conversionItems.put(Items.GOLDEN_PICKAXE,"gold");
        conversionItems.put(Items.GOLDEN_SHOVEL,"gold");
        conversionItems.put(Items.GOLD_INGOT,"gold");

        conversionItems.put(Items.IRON_HELMET,"iron");
        conversionItems.put(Items.IRON_CHESTPLATE,"iron");
        conversionItems.put(Items.IRON_LEGGINGS,"iron");
        conversionItems.put(Items.IRON_BOOTS,"iron");
        conversionItems.put(Items.IRON_SWORD,"iron");
        conversionItems.put(Items.IRON_AXE,"iron");
        conversionItems.put(Items.IRON_PICKAXE,"iron");
        conversionItems.put(Items.IRON_SHOVEL,"iron");
        conversionItems.put(Items.IRON_INGOT,"iron");

        conversionItems.put(Items.DIAMOND_HELMET,"diamond");
        conversionItems.put(Items.DIAMOND_CHESTPLATE,"diamond");
        conversionItems.put(Items.DIAMOND_LEGGINGS,"diamond");
        conversionItems.put(Items.DIAMOND_BOOTS,"diamond");
        conversionItems.put(Items.DIAMOND_SWORD,"diamond");
        conversionItems.put(Items.DIAMOND_AXE,"diamond");
        conversionItems.put(Items.DIAMOND_PICKAXE,"diamond");
        conversionItems.put(Items.DIAMOND_SHOVEL,"diamond");
        conversionItems.put(Items.DIAMOND,"diamond");
    }
}
