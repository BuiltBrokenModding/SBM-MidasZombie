package com.builtbroken.midaszombie.materials;

import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
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

    //Custom tool classes for internal tracking
    public static final String TOOL_SWORD = "sword";
    public static final String TOOL_HOE = "hoe";
    public static final String ITEM_INGOT = "ingot";

    //MC tool classes
    public static final String TOOL_SHOVEL = "shovel";
    public static final String TOOL_AXE = "axe";
    public static final String TOOL_PICKAXE = "pickaxe";

    public static void setup()
    {
        //init types
        final MidasMaterial leather = getOrCreateMaterial(LEATHER_TYPE);
        final MidasMaterial iron = getOrCreateMaterial(IRON_TYPE);
        final MidasMaterial chain = getOrCreateMaterial(CHAIN_TYPE);
        final MidasMaterial gold = getOrCreateMaterial(GOLD_TYPE);
        final MidasMaterial diamond = getOrCreateMaterial(DIAMOND_TYPE);

        //init armor
        armorMatToTypeMat.put(ItemArmor.ArmorMaterial.LEATHER, LEATHER_TYPE);
        armorMatToTypeMat.put(ItemArmor.ArmorMaterial.IRON, IRON_TYPE);
        armorMatToTypeMat.put(ItemArmor.ArmorMaterial.CHAIN, CHAIN_TYPE);
        armorMatToTypeMat.put(ItemArmor.ArmorMaterial.GOLD, GOLD_TYPE);
        armorMatToTypeMat.put(ItemArmor.ArmorMaterial.DIAMOND, DIAMOND_TYPE);

        //Leather
        leather.setDefault(EntityEquipmentSlot.HEAD, Items.LEATHER_HELMET);
        leather.setDefault(EntityEquipmentSlot.CHEST, Items.LEATHER_CHESTPLATE);
        leather.setDefault(EntityEquipmentSlot.LEGS, Items.LEATHER_LEGGINGS);
        leather.setDefault(EntityEquipmentSlot.FEET, Items.LEATHER_BOOTS);

        //Iron
        iron.setDefault(EntityEquipmentSlot.HEAD, Items.IRON_HELMET);
        iron.setDefault(EntityEquipmentSlot.CHEST, Items.IRON_CHESTPLATE);
        iron.setDefault(EntityEquipmentSlot.LEGS, Items.IRON_LEGGINGS);
        iron.setDefault(EntityEquipmentSlot.FEET, Items.IRON_BOOTS);
        
        iron.setDefault(TOOL_SWORD, Items.IRON_SWORD);
        iron.setDefault(TOOL_SHOVEL, Items.IRON_SHOVEL);
        iron.setDefault(TOOL_AXE, Items.IRON_AXE);
        iron.setDefault(TOOL_PICKAXE, Items.IRON_PICKAXE);
        iron.setDefault(TOOL_HOE, Items.IRON_HOE);

        iron.setDefault(ITEM_INGOT, Items.IRON_INGOT);

        //Chain
        chain.setDefault(EntityEquipmentSlot.HEAD, Items.CHAINMAIL_HELMET);
        chain.setDefault(EntityEquipmentSlot.CHEST, Items.CHAINMAIL_CHESTPLATE);
        chain.setDefault(EntityEquipmentSlot.LEGS, Items.CHAINMAIL_LEGGINGS);
        chain.setDefault(EntityEquipmentSlot.FEET, Items.CHAINMAIL_BOOTS);

        //Gold
        gold.setDefault(EntityEquipmentSlot.HEAD, Items.GOLDEN_HELMET);
        gold.setDefault(EntityEquipmentSlot.CHEST, Items.GOLDEN_CHESTPLATE);
        gold.setDefault(EntityEquipmentSlot.LEGS, Items.GOLDEN_LEGGINGS);
        gold.setDefault(EntityEquipmentSlot.FEET, Items.GOLDEN_BOOTS);

        gold.setDefault(TOOL_SWORD, Items.GOLDEN_SWORD);
        gold.setDefault(TOOL_SHOVEL, Items.GOLDEN_SHOVEL);
        gold.setDefault(TOOL_AXE, Items.GOLDEN_AXE);
        gold.setDefault(TOOL_PICKAXE, Items.GOLDEN_PICKAXE);
        gold.setDefault(TOOL_HOE, Items.GOLDEN_HOE);

        gold.setDefault(ITEM_INGOT, Items.GOLD_INGOT);

        //Diamond
        diamond.setDefault(EntityEquipmentSlot.HEAD, Items.DIAMOND_HELMET);
        diamond.setDefault(EntityEquipmentSlot.CHEST, Items.DIAMOND_CHESTPLATE);
        diamond.setDefault(EntityEquipmentSlot.LEGS, Items.DIAMOND_LEGGINGS);
        diamond.setDefault(EntityEquipmentSlot.FEET, Items.DIAMOND_BOOTS);

        diamond.setDefault(TOOL_SWORD, Items.DIAMOND_SWORD);
        diamond.setDefault(TOOL_SHOVEL, Items.DIAMOND_SHOVEL);
        diamond.setDefault(TOOL_AXE, Items.DIAMOND_AXE);
        diamond.setDefault(TOOL_PICKAXE, Items.DIAMOND_PICKAXE);
        diamond.setDefault(TOOL_HOE, Items.DIAMOND_HOE);

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
            else if(item instanceof ItemSword)
            {
                addSimpleTool(TOOL_SWORD, item);
            }
            else if(item instanceof ItemHoe)
            {
                addSimpleTool(TOOL_HOE, item);
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
        for (Map.Entry<String, Set<Item>> toolSet : toolTypes.entrySet())
        {
            final String toolClass = toolSet.getKey();

            for (Item item : toolSet.getValue())
            {
                for (MidasMaterial midasMaterial : materialNameToType.values())
                {
                    final Item defaultItem = midasMaterial.getDefault(toolClass);
                    if (defaultItem != item)
                    {
                        midasMaterial.addSimpleConversion(item, defaultItem);
                    }
                }
            }
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
