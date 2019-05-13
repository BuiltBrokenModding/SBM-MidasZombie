package com.builtbroken.midaszombie.entity;

import com.builtbroken.midaszombie.ConfigMain;
import com.builtbroken.midaszombie.MidasZombie;
import com.builtbroken.midaszombie.materials.MaterialRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = MidasZombie.MOD_ID)
public class ModEventHandler
{
    @SubscribeEvent
    public static void onMobAttack(LivingHurtEvent event)
    {
        if (event.getSource() != null)
        {
            final Entity source = event.getSource().getTrueSource();
            final EntityLivingBase target = event.getEntityLiving();

            if (source instanceof EntityIronicZombie && target != null)
            {
                final EntityIronicZombie zombie = (EntityIronicZombie) source;

                //Only run on active zombies
                if (zombie.hasMidasTouch())
                {
                    //Run per equipment slot
                    final ResourceLocation type = zombie.getMaterialType();
                    for (EntityEquipmentSlot slot : EntityEquipmentSlot.values())
                    {
                        applyEffectToSlot(target, slot, type);
                    }

                    //TODO consider random chance for inventory
                }

                //TODO play sound effects
                //TODO spawn particle effects

                //Consume magic points
                zombie.consumeTransfer(1);
            }
        }
    }

    public static void applyEffectToSlot(EntityLivingBase player, EntityEquipmentSlot slot, ResourceLocation type)
    {
        final ItemStack currentItemStack = player.getItemStackFromSlot(slot);
        if (!currentItemStack.isEmpty() && ConfigMain.isAllowed(currentItemStack.getItem()) && Math.random() < ConfigMain.CONVERSION_CHANCE)
        {
            //Get conversion
            final ItemStack newStack = MaterialRegistry.getConversion(currentItemStack, type);

            //Confirm item changed
            if (!ItemStack.areItemsEqual(currentItemStack, newStack) || !ItemStack.areItemStackTagsEqual(newStack, currentItemStack))
            {
                player.setItemStackToSlot(slot, newStack);
            }
        }
    }
}