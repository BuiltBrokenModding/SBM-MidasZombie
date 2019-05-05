package com.builtbroken.midaszombie;

import com.builtbroken.midaszombie.entity.EntityIronicZombie;
import net.minecraft.client.renderer.entity.RenderZombie;
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
    }
}
