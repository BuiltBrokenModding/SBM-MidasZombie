package com.builtbroken.midaszombie;

import net.minecraftforge.common.config.Config;


@Config(modid = MidasZombie.MOD_ID)

public class ModConfig
{
    @Config.Name("chance of conversion")
    public static double CONVERSION_CHANCE = 1;

    @Config.Name("Spawn Weight")
    public static int SPAWN_WEIGHT = 100;

    @Config.Name("Transfer limit")
    public static int MAX_TRANSFERS = 5;
    @Config.Name("Time Limit in seconds")
    public static int TIME_LIMIT = 200;

    @Config.Name("Advanced Options")
    public static AdvancedOption advOpt = new AdvancedOption();

    public static class AdvancedOption
    {

    }

}
