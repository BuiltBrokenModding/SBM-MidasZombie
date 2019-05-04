package com.builtbroken.midaszombie;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;

import static com.builtbroken.midaszombie.ModConfig.advOpt;

public class MaterialRegistry
{
    public static ArrayList<String> uniqueTypesList = new ArrayList<>();
    public static int uniqueTypes = 1;
    public static HashMap<Pair<Item, String>, Item> conversionItems = new HashMap<>();

    public static void setup()
    {
        for (int i = 0; i < advOpt.inputItems.length; i++)
        {
            Item item1 = ForgeRegistries.ITEMS.getValue(new ResourceLocation(advOpt.inputItems[i]));
            Item item2 = ForgeRegistries.ITEMS.getValue(new ResourceLocation(advOpt.outputItems[i]));
            String type = advOpt.type[i];
            conversionItems.put(Pair.of(item1, type), item2);
        }
        String[] numArray = advOpt.type;
        for (String s : numArray)
        {
            if (!uniqueTypesList.contains(s))
            {
                uniqueTypesList.add(s);
            }
        }
        uniqueTypes = uniqueTypesList.size();
    }

    public static ItemStack getConversion(ItemStack stack, EntityIronicZombie zombie)
    {
        String type = zombie.getEntityData().getString("type");
        Pair<Item, String> pair = Pair.of(stack.getItem(), type);
        if (!conversionItems.containsKey(pair))
        {
            System.out.println("Item is not eligible for conversion of any kind!");
            return stack;
        }
        NBTTagCompound nbt = stack.getTagCompound();
        ItemStack newstack = new ItemStack(conversionItems.get(pair));
        newstack.setCount(stack.getCount());
        newstack.setTagCompound(nbt);
        newstack.setItemDamage(ModEventHandler.transformDamage(stack, newstack));
        System.out.println("success!");
        return newstack;
    }
    // System.out.println(conversionItems);
}
