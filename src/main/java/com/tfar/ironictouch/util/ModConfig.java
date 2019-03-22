package com.tfar.ironictouch.util;
import net.minecraftforge.common.config.Config;


@Config(modid = ReferenceVariables.MOD_ID)

public class ModConfig {
    @Config.Name("chance of conversion")
    public static double CONVERSION_CHANCE = 1;

    @Config.Name("Spawn Weight")
    public static int SPAWN_WEIGHT = 100;

    @Config.Name("Transfer limit")
    public static int MAX_TRANSFERS=5;
    @Config.Name("Time Limit in seconds")
    public static int TIME_LIMIT=200;

    @Config.Name("Advanced Options")
    public static AdvancedOption advOpt = new AdvancedOption();

    public static class AdvancedOption {

        @Config.Name("input")
        public String[] inputItems = {"minecraft:planks","minecraft:cobblestone","minecraft:iron_ingot", "minecraft:gold_ingot","minecraft:diamond","minecraft:gold_ingot"};
        @Config.Name("output")
        public String[] outputItems = {"minecraft:cobblestone", "minecraft:iron_ingot","minecraft:gold_ingot","minecraft:diamond","minecraft:gold_ingot","minecraft:iron_ingot"};
        @Config.Name("type")
        public String[] type = {"stone","iron","gold","diamond","gold","iron"};

        //public ConversionConfig[] conversionConfigs = new ConversionConfig[]{new ConversionConfig("minecraft:iron_sword", "minecraft:golden_sword", "gold")};
    }

}
