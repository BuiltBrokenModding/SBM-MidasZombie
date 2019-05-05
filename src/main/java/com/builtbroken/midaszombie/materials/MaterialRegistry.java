package com.builtbroken.midaszombie.materials;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.*;

import static java.lang.Math.floor;

public class MaterialRegistry
{
    //Defaults
    public static final ResourceLocation LEATHER_TYPE = new ResourceLocation("minecraft", "leather");
    public static final ResourceLocation IRON_TYPE = new ResourceLocation("minecraft", "iron");
    public static final ResourceLocation CHAIN_TYPE = new ResourceLocation("minecraft", "chain");
    public static final ResourceLocation GOLD_TYPE = new ResourceLocation("minecraft", "gold");
    public static final ResourceLocation DIAMOND_TYPE = new ResourceLocation("minecraft", "diamond");

    //Registered
    private static final HashMap<ResourceLocation, MidasMaterial> materialNameToType = new HashMap<>();
    private static final List<ResourceLocation> materialTypes = new ArrayList();
    private static final HashMap<ItemArmor.ArmorMaterial, ResourceLocation> armorMatToTypeMat = new HashMap();

    //Generated
    private static final HashMap<EntityEquipmentSlot, Set<Item>> armorTypes = new HashMap();
    private static final HashMap<String, Set<Item>> toolTypes = new HashMap();

    public static void setup()
    {
        //init types
        getOrCreateMaterial(LEATHER_TYPE);
        getOrCreateMaterial(IRON_TYPE);
        getOrCreateMaterial(CHAIN_TYPE);
        getOrCreateMaterial(GOLD_TYPE);
        getOrCreateMaterial(DIAMOND_TYPE);

        //init armor
        armorMatToTypeMat.put(ItemArmor.ArmorMaterial.LEATHER, LEATHER_TYPE);
        armorMatToTypeMat.put(ItemArmor.ArmorMaterial.IRON, IRON_TYPE);
        armorMatToTypeMat.put(ItemArmor.ArmorMaterial.CHAIN, CHAIN_TYPE);
        armorMatToTypeMat.put(ItemArmor.ArmorMaterial.GOLD, GOLD_TYPE);
        armorMatToTypeMat.put(ItemArmor.ArmorMaterial.DIAMOND, DIAMOND_TYPE);

        //Collect items to sets of items
        for (Item item : ForgeRegistries.ITEMS.getValuesCollection())
        {
            if (item instanceof ItemArmor)
            {
                addSimpleArmor(((ItemArmor) item).armorType, item);
            }
            else if (item instanceof ItemTool)
            {
                item.getToolClasses(new ItemStack(item)).forEach(toolClass -> addSimpleTool(toolClass, item));
            }
        }

        //Mapping armor types to conversions
        for (Map.Entry<EntityEquipmentSlot, Set<Item>> armorSet : armorTypes.entrySet())
        {
            final EntityEquipmentSlot slot = armorSet.getKey();

            //Loop all possible armors
            for (Item item : armorSet.getValue())
            {
                //Double check that it is armor
                if (item instanceof ItemArmor)
                {
                    //Get the material
                    ItemArmor.ArmorMaterial armorMaterial = ((ItemArmor) item).getArmorMaterial();
                    for (ItemArmor.ArmorMaterial otherMaterial : ItemArmor.ArmorMaterial.values())
                    {
                        //Only add for materials that are not ours
                        if (otherMaterial != armorMaterial)
                        {
                            final ResourceLocation materialName = armorMatToTypeMat.get(otherMaterial);
                            final MidasMaterial material = getMaterial(materialName);
                            material.addSimpleConversion(item, material.getDefault(slot));
                        }
                    }
                }
            }
        }

        //Mapping tool types to conversion
        for()
        {

        }
    }

    public static void addSimpleTool(String type, Item item)
    {
        if (type != null && !type.trim().isEmpty())
        {
            if (!toolTypes.containsKey(type.toLowerCase()))
            {
                toolTypes.put(type.toLowerCase(), new HashSet());
            }
            toolTypes.get(type.toLowerCase()).add(item);
        }
    }

    public static void addSimpleArmor(EntityEquipmentSlot slot, Item item)
    {
        if (!armorTypes.containsKey(slot))
        {
            armorTypes.put(slot, new HashSet());
        }
        armorTypes.get(slot).add(item);
    }

    public static ResourceLocation getRandomMaterial()
    {
        return materialTypes.get((int) floor(Math.random() * materialTypes.size()));
    }

    public static MidasMaterial getMaterial(ResourceLocation resourceLocation)
    {
        return resourceLocation != null ? materialNameToType.get(resourceLocation) : null;
    }

    public static MidasMaterial getOrCreateMaterial(ResourceLocation resourceLocation)
    {
        MidasMaterial midasMaterial = getMaterial(resourceLocation);
        if (midasMaterial == null)
        {
            midasMaterial = new MidasMaterial(resourceLocation);
            materialNameToType.put(resourceLocation, midasMaterial);
            materialTypes.add(resourceLocation);
        }
        return midasMaterial;
    }

    public static ItemStack getConversion(ItemStack stack, ResourceLocation type)
    {
        final MidasMaterial midasMaterial = getMaterial(type);
        if (midasMaterial != null)
        {
            return midasMaterial.convertItemStack(stack);
        }
        return stack;
    }
}
