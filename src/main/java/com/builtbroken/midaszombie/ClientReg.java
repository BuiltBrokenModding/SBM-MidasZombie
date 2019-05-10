package com.builtbroken.midaszombie;

import com.builtbroken.midaszombie.entity.EntityIronicZombie;
import com.builtbroken.midaszombie.entity.RenderIronicZombie;
import com.builtbroken.midaszombie.materials.MaterialRegistry;
import net.minecraft.client.renderer.entity.RenderZombie;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by Dark(DarkGuardsman, Robert) on 5/4/2019.
 */
@Mod.EventBusSubscriber(modid = MidasZombie.MOD_ID)
public class ClientReg
{
    @SubscribeEvent
    public static void initModels(ModelRegistryEvent event)
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityIronicZombie.class, RenderZombie::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityIronicZombie.class, renderManagerIn -> new RenderIronicZombie(renderManagerIn));

        RenderIronicZombie.typeToTexture.put(MaterialRegistry.STONE_TYPE, new ResourceLocation(MidasZombie.MOD_ID,"textures/entity/zombie/stone.png"));
        RenderIronicZombie.typeToTexture.put(MaterialRegistry.LEATHER_TYPE, new ResourceLocation(MidasZombie.MOD_ID,"textures/entity/zombie/leather.png"));
        RenderIronicZombie.typeToTexture.put(MaterialRegistry.IRON_TYPE, new ResourceLocation(MidasZombie.MOD_ID,"textures/entity/zombie/iron.png"));
        RenderIronicZombie.typeToTexture.put(MaterialRegistry.CHAIN_TYPE, new ResourceLocation(MidasZombie.MOD_ID,"textures/entity/zombie/chain.png"));
        RenderIronicZombie.typeToTexture.put(MaterialRegistry.GOLD_TYPE, new ResourceLocation(MidasZombie.MOD_ID,"textures/entity/zombie/gold.png"));
        RenderIronicZombie.typeToTexture.put(MaterialRegistry.DIAMOND_TYPE, new ResourceLocation(MidasZombie.MOD_ID,"textures/entity/zombie/diamond.png"));
    }
}
