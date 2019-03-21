package com.tfar.ironictouch;

import javafx.util.Pair;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import static com.tfar.ironictouch.util.ModConfig.advOpt;
import static com.tfar.ironictouch.util.ReferenceVariables.*;
import static com.tfar.ironictouch.util.ReferenceVariables.uniqueTypesList;

public class MaterialRegistry {

    public static void setup() {
        for (int i = 0; i < advOpt.inputItems.length; i++) {
            Item item1 = ForgeRegistries.ITEMS.getValue(new ResourceLocation(advOpt.inputItems[i]));
            Item item2 = ForgeRegistries.ITEMS.getValue(new ResourceLocation(advOpt.outputItems[i]));
            String type = advOpt.type[i];
            conversionItems.put(item1, new Pair<>(item2, type));
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
