package com.builtbroken.midaszombie;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;

import static com.builtbroken.midaszombie.ModConfig.CONVERSION_CHANCE;

@Mod.EventBusSubscriber(modid = MidasZombie.MOD_ID)
public class ModEventHandler
{
    @SubscribeEvent
    public static void onMobAttack(LivingHurtEvent event)
    {
        //null check
        if (event.getSource() == null)
        {
            return;
        }
        //is it an ironic zombie and is the player being attacked?
        if (!(event.getSource().getTrueSource() instanceof EntityIronicZombie) || !(event.getEntityLiving() instanceof EntityPlayer))
        {
            return;
        }
        EntityIronicZombie zombie = (EntityIronicZombie) event.getSource().getTrueSource();

        //are there transfers left or time remaining
        if (zombie.getActivePotionEffect(MobEffects.GLOWING) == null)
        {
            return;
        }
        EntityPlayer player = (EntityPlayer) event.getEntityLiving();
        HashMap<EntityEquipmentSlot, ItemStack> equipment = new HashMap<>();
        equipment.put(EntityEquipmentSlot.HEAD, player.getItemStackFromSlot(EntityEquipmentSlot.HEAD));
        equipment.put(EntityEquipmentSlot.CHEST, player.getItemStackFromSlot(EntityEquipmentSlot.CHEST));
        equipment.put(EntityEquipmentSlot.LEGS, player.getItemStackFromSlot(EntityEquipmentSlot.LEGS));
        equipment.put(EntityEquipmentSlot.FEET, player.getItemStackFromSlot(EntityEquipmentSlot.FEET));
        equipment.put(EntityEquipmentSlot.MAINHAND, player.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND));

        for (EntityEquipmentSlot slot : equipment.keySet())
        {
            if (!randCheck() || equipment.get(slot).isEmpty())
            {
                continue;
            }
            ItemStack oldStack = equipment.get(slot);
            ItemStack newStack = MaterialRegistry.getConversion(oldStack, zombie);
            if (newStack == oldStack)
            {
                continue;
            }
            player.setItemStackToSlot(slot, ItemStack.EMPTY);
            player.setItemStackToSlot(slot, newStack);
            int i = zombie.getEntityData().getInteger("transfers");
            zombie.getEntityData().setInteger("transfers", i - 1);
        }

        if (zombie.getEntityData().getInteger("transfers") <= 0)
        {
            zombie.clearActivePotions();
        }
    }

    public static boolean randCheck()
    {
        return Math.random() < CONVERSION_CHANCE;
    }

    public static int transformDamage(ItemStack oldStack, ItemStack newStack)
    {
        int damage1 = oldStack.getItemDamage();
        int maxdamage1 = oldStack.getMaxDamage();
        int maxdamage2 = newStack.getMaxDamage();
        if (maxdamage1 != 0)
        {
            return damage1 * maxdamage2 / maxdamage1;
        }
        return 0;
    }
}