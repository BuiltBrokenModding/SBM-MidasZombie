package com.builtbroken.midaszombie.entity;

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

import static com.builtbroken.midaszombie.ModConfig.CONVERSION_CHANCE;

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

                //Consume magic points
                zombie.consumeTransfer(1);
            }
        }
    }

    @SubscribeEvent
    public static void onMobJoinWorld(EntityJoinWorldEvent event)
    {
        if (event.getEntity() instanceof EntityZombie)
        {
            for (EntityEquipmentSlot entityequipmentslot : EntityEquipmentSlot.values())
            {
                if (entityequipmentslot.getSlotType() == EntityEquipmentSlot.Type.ARMOR)
                {
                    Item item = EntityZombie.getArmorByChance(entityequipmentslot, 3);
                    if (item != null)
                    {
                        event.getEntity().setItemStackToSlot(entityequipmentslot, new ItemStack(item));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onMobInteraction(PlayerInteractEvent.EntityInteract event)
    {
        if (event.getTarget() instanceof EntityZombie && event.getEntityPlayer().isCreative())
        {
            ItemStack heldItem = event.getItemStack();
            if (heldItem.getItem() instanceof ItemArmor)
            {
                EntityEquipmentSlot armorType = ((ItemArmor) heldItem.getItem()).armorType;
                event.getTarget().setItemStackToSlot(armorType, heldItem.copy());
            }
            else if (heldItem.getItem() instanceof ItemTool || heldItem.getItem() instanceof ItemSword)
            {
                event.getTarget().setItemStackToSlot(EntityEquipmentSlot.MAINHAND, heldItem.copy());
            }
        }
    }

    public static void applyEffectToSlot(EntityLivingBase player, EntityEquipmentSlot slot, ResourceLocation type)
    {
        final ItemStack currentItemStack = player.getItemStackFromSlot(slot);
        if (!currentItemStack.isEmpty() && Math.random() < CONVERSION_CHANCE)
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