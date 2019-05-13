package com.builtbroken.midaszombie;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;


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

    @Config.Name("exclusion_list")
    @Config.Comment("List of items to exclude from conversion effects")
    @Config.LangKey("config." + MidasZombie.MOD_ID + ":list.exclusion")
    public static List<String> exclusionList = new ArrayList();

    @Config.Name("exclusion_list_invert")
    @Config.Comment("Should the exclusion list be used as a include only list")
    @Config.LangKey("config." + MidasZombie.MOD_ID + ":list.exclusion.invert")
    public static boolean exclusionListInvert = false;

    @Config.Name("hard_error")
    @Config.Comment("If there is an error loading the config produce a crash rather than letting the game continue.")
    @Config.LangKey("config." + MidasZombie.MOD_ID + ":error.hard")
    public static boolean hardErrorConfig = true;

    @Config.Ignore
    private static List<ResourceLocation> _exludedItemsCache = new ArrayList();

    public static void init()
    {
        //Clear cache
        _exludedItemsCache.clear();

        //Create item cache
        for (String name : exclusionList)
        {
            name = name.trim();
            if (!name.isEmpty())
            {
                final ResourceLocation regName = new ResourceLocation(name);
                final Item item = ForgeRegistries.ITEMS.getValue(regName);
                if (item != null && item != Items.AIR)
                {
                    _exludedItemsCache.add(regName);
                }
                else
                {
                    error("Config#Init() - Failed to locate item by the name of '" + name
                            + "' for exclusion list. This may cause unexpected results in gameplay.");
                }
            }
            else
            {
                error("Config#Init() - Empty String detected in exclusion item list");
            }
        }
    }

    private static void error(String message)
    {
        if (hardErrorConfig)
        {

            throw new RuntimeException(MidasZombie.logger.getName()
                    + " Hard fail was enabled for config, crashing due to error >>"
                    + message);
        }
        else
        {
            MidasZombie.logger.error(message);
        }
    }

    @SubscribeEvent
    public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.getModID().equals(MidasZombie.MOD_ID))
        {
            ConfigManager.sync(MidasZombie.MOD_ID, Config.Type.INSTANCE);
            init();
        }
    }

    /**
     * Is the item allowed by the config
     *
     * @param item - item to check
     * @return true if allowed
     */
    public static boolean isAllowed(@Nonnull Item item)
    {
        if (exclusionListInvert)
        {
            return _exludedItemsCache.contains(item.getRegistryName());
        }
        return _exludedItemsCache.isEmpty() || !_exludedItemsCache.contains(item.getRegistryName());
    }
}
