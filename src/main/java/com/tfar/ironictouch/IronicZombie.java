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
import org.apache.logging.log4j.Logger;

@Mod(modid = ReferenceVariables.MOD_ID, name = ReferenceVariables.NAME, version = ReferenceVariables.VERSION)
public class IronicZombie
{
    @Mod.Instance
    public static IronicZombie instance;
    @SidedProxy(clientSide = ReferenceVariables.CLIENT_PROXY_CLASS, serverSide = ReferenceVariables.COMMON_PROXY_CLASS)
    public static CommonProxy proxy;
    public static Logger logger;
    @EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }
    @EventHandler
    public static void init(FMLInitializationEvent event) {
        MaterialRegistry.setup();
        MinecraftForge.EVENT_BUS.register(new ModEventHandler());
    }
    @EventHandler
    public static void postInit(FMLPostInitializationEvent event) {
    }
}

