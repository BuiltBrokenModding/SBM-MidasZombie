package com.tfar.ironictouch.proxy;

import com.tfar.ironictouch.ModEntities;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
        ModEntities.init();
    }
}
