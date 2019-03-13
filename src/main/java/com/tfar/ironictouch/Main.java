package com.tfar.ironictouch;

import com.tfar.ironictouch.proxy.CommonProxy;
import com.tfar.ironictouch.util.ReferenceVariables;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ReferenceVariables.MOD_ID, name = ReferenceVariables.NAME, version = ReferenceVariables.VERSION)
public class Main
{
    @Mod.Instance
    public static Main instance;
    @SidedProxy(clientSide = ReferenceVariables.CLIENT_PROXY_CLASS, serverSide = ReferenceVariables.COMMON_PROXY_CLASS)
    public static CommonProxy proxy;
    public static Conversion setup;
    @EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        setup.setup();
        proxy.preInit(event);
    }
    @EventHandler
    public static void init(FMLInitializationEvent event) { {
        MinecraftForge.EVENT_BUS.register(new ModEventHandler());
    }

    }
    @EventHandler
    public static void postInit(FMLPostInitializationEvent event) {
    }
}

