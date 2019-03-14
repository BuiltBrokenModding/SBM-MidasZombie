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
    @Config.Name("Conversion Type")
    public static String CONVERSION_TYPE ="diamond";
}
