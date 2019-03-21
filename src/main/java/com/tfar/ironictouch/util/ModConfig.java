package com.tfar.ironictouch.util;
import net.minecraftforge.common.config.Config;


@Config(modid = ReferenceVariables.MOD_ID)

public class ModConfig {
    @Config.Name("chance of conversion")
    public static double CONVERSION_CHANCE = .4;

    @Config.Name("Spawn Weight")
    public static int SPAWN_WEIGHT = 100;

    @Config.Name("Transfer limit")
    public static int MAX_TRANSFERS=5;
    @Config.Name("Time Limit in seconds")
    public static int TIME_LIMIT=100;

    @Config.Name("Advanced Options")
    public static AdvancedOption advOpt = new AdvancedOption();

    public static class AdvancedOption {

        @Config.Name("item1")
        public String[] inputItems = {"minecraft:stone_sword","minecraft:iron_sword","minecraft:golden_sword"};
        @Config.Name("item2")
        public String[] outputItems = {"minecraft:iron_sword", "minecraft:golden_sword","minecraft:diamond_sword"};
        @Config.Name("type")
        public String[] type = {"iron","gold","diamond"};

        //public ConversionConfig[] conversionConfigs = new ConversionConfig[]{new ConversionConfig("minecraft:iron_sword", "minecraft:golden_sword", "gold")};
    }

}
