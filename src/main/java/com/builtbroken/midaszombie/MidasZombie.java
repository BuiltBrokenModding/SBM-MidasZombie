package com.builtbroken.midaszombie;

import com.builtbroken.midaszombie.entity.DataSerializerResourceLocation;
import com.builtbroken.midaszombie.entity.EntityIronicZombie;
import com.builtbroken.midaszombie.entity.ModEventHandler;
import com.builtbroken.midaszombie.materials.MaterialRegistry;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static com.builtbroken.midaszombie.ConfigMain.SPAWN_WEIGHT;

@Mod(modid = MidasZombie.MOD_ID, name = MidasZombie.NAME, version = MidasZombie.VERSION)
@Mod.EventBusSubscriber
public class MidasZombie
{
    public static final String MOD_ID = "midaszombie";
    public static final String NAME = "[SBM] Midas Zombie";
    public static final String VERSION = "1.0";

    @EventHandler
    public static void init(FMLInitializationEvent event)
    {
        DataSerializers.registerSerializer(DataSerializerResourceLocation.INSTANCE);
        MaterialRegistry.setup();
        MinecraftForge.EVENT_BUS.register(new ModEventHandler());
    }

    @SubscribeEvent
    public static void registerEntity(RegistryEvent.Register<EntityEntry> event)
    {
        //Biomes
        List<Biome> spawnBiomes = new ArrayList<>(); //TODO config driven spawning
        spawnBiomes.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.BEACH));
        spawnBiomes.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.PLAINS));
        spawnBiomes.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.FOREST));
        spawnBiomes.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.HILLS));
        spawnBiomes.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.SWAMP));
        spawnBiomes.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.DRY));
        spawnBiomes.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.COLD));

        //Setup
        EntityEntryBuilder builder = EntityEntryBuilder.create();
        ResourceLocation name = new ResourceLocation(MidasZombie.MOD_ID, "zombie");
        builder.name(name.toString());
        builder.id(name, 0);
        builder.tracker(64, 3, true);
        builder.entity(EntityIronicZombie.class);
        builder.egg(0x222222, 0x555555);
        builder.factory((Function<World, EntityZombie>) world -> new EntityIronicZombie(world));
        builder.spawn(EnumCreatureType.MONSTER, SPAWN_WEIGHT, 1, 4, spawnBiomes.toArray(new Biome[spawnBiomes.size()]));

        //Build and register
        event.getRegistry().register(builder.build());
    }
}

