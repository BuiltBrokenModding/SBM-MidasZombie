package com.builtbroken.midaszombie.materials;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.function.Function;

/**
 * Created by Dark(DarkGuardsman, Robert) on 5/5/2019.
 */
public class MidasMaterial
{
    public final ResourceLocation regName;

    public final HashMap<Item, Function<ItemStack, ItemStack>> conversions = new HashMap();

    public final HashMap<EntityEquipmentSlot, Item> defaultArmor = new HashMap();
    public final HashMap<String, Item> defaultTools = new HashMap();

    public MidasMaterial(ResourceLocation regName)
    {
        this.regName = regName;
    }

    /**
     * Adds a simple item to item conversion. This is idea for armor and weapons.
     *
     * @param input  - input item to convert, EX: iron armor
     * @param output - output item to turn the input into, Ex: gold armor
     */
    public void addSimpleConversion(final Item input, final Item output)
    {
        if(input != null && output != null)
        {
            conversions.put(input, (itemstack) -> {
                ItemStack newStack = new ItemStack(output, itemstack.getCount(), 0);

                //Pull in damage of the item, for armor this will percent convert the damage
                if (newStack.isItemStackDamageable())
                {
                    newStack.setItemDamage(convertDamage(input.getMaxDamage(itemstack), output.getMaxDamage(newStack), itemstack.getItemDamage()));
                }

                //Pull in NBT
                if (itemstack.getTagCompound() != null)
                {
                    newStack.setTagCompound(itemstack.getTagCompound().copy());
                }
                return newStack;
            });
        }
    }

    /**
     * Used to convert old damage to new damage scale
     *
     * @param oldMax    - old scale max, Ex: 100
     * @param newMax    - new scale max, Ex: 10
     * @param oldDamage - damage, Ex: 10
     * @return scaled damage, Ex: 1
     */
    public int convertDamage(int oldMax, int newMax, int oldDamage)
    {
        if (newMax != oldMax)
        {
            float percentage = oldDamage / (float) oldMax;
            return Math.min(newMax, (int) Math.ceil(percentage * newMax));
        }
        return newMax;
    }

    /**
     * Converts the itemstack into another itemstack if it is supported
     *
     * @param stack - stack to convert
     * @return converted stack, or same stack if not converted
     */
    public ItemStack convertItemStack(ItemStack stack)
    {
        if (!stack.isEmpty())
        {
            Function<ItemStack, ItemStack> function = conversions.get(stack.getItem());
            if (function != null)
            {
                return function.apply(stack);
            }
        }
        return stack;
    }

    public Item getDefault(EntityEquipmentSlot slot)
    {
        return defaultArmor.get(slot);
    }

    public Item getDefault(String toolClass)
    {
        return defaultTools.get(toolClass);
    }

    public void setDefault(EntityEquipmentSlot head, ItemArmor leatherHelmet)
    {
        defaultArmor.put(head, leatherHelmet);
    }

    public void setDefault(String toolClass, Item item)
    {
        defaultTools.put(toolClass, item);
    }
}
