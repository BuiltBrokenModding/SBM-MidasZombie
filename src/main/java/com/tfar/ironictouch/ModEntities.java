package com.tfar.ironictouch;

import com.tfar.ironictouch.util.ReferenceVariables;
import net.minecraft.client.renderer.entity.RenderZombie;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

import static com.tfar.ironictouch.util.ModConfig.SPAWN_WEIGHT;

public class ModEntities {
    public static void init() {
        int id = 1;
        List<Biome> spawnBiomes = new ArrayList<>();
        spawnBiomes.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.BEACH));
        spawnBiomes.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.PLAINS));
        spawnBiomes.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.FOREST));
        spawnBiomes.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.HILLS));
        spawnBiomes.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.SWAMP));
        spawnBiomes.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.DRY));
        spawnBiomes.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.COLD));

        EntityRegistry.registerModEntity(new ResourceLocation(ReferenceVariables.MOD_ID, "ironictouch_ironiczombie"), EntityIronicZombie.class, "ironictouch_ironiczombie", id,
                Main.instance, 64, 3, true, 0x222222, 0x555555);
        EntityRegistry.addSpawn(EntityIronicZombie.class, SPAWN_WEIGHT, 1, 4, EnumCreatureType.MONSTER,  spawnBiomes.toArray(new Biome[spawnBiomes.size()]));

    }
    @SideOnly(Side.CLIENT)
    public static void initModels() {
        RenderingRegistry.registerEntityRenderingHandler(EntityIronicZombie.class, RenderZombie::new);
    }
}
