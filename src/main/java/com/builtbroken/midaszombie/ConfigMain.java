package com.builtbroken.midaszombie;

import net.minecraftforge.common.config.Config;


@Config(modid = MidasZombie.MOD_ID)
@Config.LangKey("config." + MidasZombie.MOD_ID + ":title")
public class ConfigMain
{
    @Config.Name("conversion_chance")
    @Config.RangeDouble(min = 0, max = 1)
    @Config.Comment("Chance that each item is converted. Will run per item, not once for all items.")
    @Config.LangKey("config." + MidasZombie.MOD_ID + ":conversion.chance")
    public static double CONVERSION_CHANCE = 1;

    @Config.Name("spawn_weight")
    @Config.RangeInt(min = 1)
    @Config.Comment("Spawn weight of the zombie, aka how likely it is to spawn in the world")
    @Config.LangKey("config." + MidasZombie.MOD_ID + ":spawn.weight")
    public static int SPAWN_WEIGHT = 100;

    @Config.Name("conversion_limit")
    @Config.RangeInt(min = 1)
    @Config.Comment("Amount of tries a zombie gets before the midas effect is removed")
    @Config.LangKey("config." + MidasZombie.MOD_ID + ":conversion.limit")
    public static int MAX_TRANSFERS = 5;

    @Config.Name("conversion_timer")
    @Config.RangeInt(min = 20)
    @Config.Comment("Amount of time in ticks (20 ticks a second) to allow zombies to keep midas effect after spawning")
    @Config.LangKey("config." + MidasZombie.MOD_ID + ":conversion.timer")
    public static int TIME_LIMIT = 2000;
    @SubscribeEvent
    public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.getModID().equals(MidasZombie.MOD_ID))
        {
            ConfigManager.sync(MidasZombie.MOD_ID, Config.Type.INSTANCE);
            init();
        }
    }

}