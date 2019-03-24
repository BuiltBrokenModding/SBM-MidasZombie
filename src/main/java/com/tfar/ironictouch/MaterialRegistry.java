package com.tfar.ironictouch;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.apache.commons.lang3.tuple.Pair;

import static com.tfar.ironictouch.util.ModConfig.advOpt;
import static com.tfar.ironictouch.util.ReferenceVariables.*;

public class MaterialRegistry {

    public static void setup() {
        for (int i = 0; i < advOpt.inputItems.length; i++) {
            Item item1 = ForgeRegistries.ITEMS.getValue(new ResourceLocation(advOpt.inputItems[i]));
            Item item2 = ForgeRegistries.ITEMS.getValue(new ResourceLocation(advOpt.outputItems[i]));
            String type = advOpt.type[i];
            conversionItems.put(Pair.of(item1,type),item2);
        }
        String[] numArray = advOpt.type;
        for (String s : numArray) {
            if (!uniqueTypesList.contains(s)) {
                uniqueTypesList.add(s);
            }
        }
        uniqueTypes = uniqueTypesList.size();
    }
    // System.out.println(conversionItems);
}
