package com.tfar.ironictouch;

import com.tfar.ironictouch.util.ReferenceVariables;
import javafx.util.Pair;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.tfar.ironictouch.util.ModConfig.CONVERSION_CHANCE;
import static com.tfar.ironictouch.util.ReferenceVariables.conversionItems;

@Mod.EventBusSubscriber(modid = ReferenceVariables.MOD_ID)
public class ModEventHandler {
    public ModEventHandler() {
    }

    @SubscribeEvent
    public static void onMobAttack(LivingHurtEvent event) {
        //null check
        if (event.getSource() == null) return;
        //is it an ironic zombie and is the player being attacked?
        if (!(event.getSource().getTrueSource() instanceof EntityIronicZombie) || !(event.getEntityLiving() instanceof EntityPlayer)) {
            return;
        }
        if (event.getSource().getTrueSource().getEntityData().getInteger("transfers") <= 0 || ((EntityIronicZombie) event.getSource().getTrueSource()).getActivePotionEffect(MobEffects.GLOWING) == null)
            return;
        EntityPlayer player = (EntityPlayer) event.getEntityLiving();
        ItemStack head = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
        ItemStack chest = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
        ItemStack legs = player.getItemStackFromSlot(EntityEquipmentSlot.LEGS);
        ItemStack feet = player.getItemStackFromSlot(EntityEquipmentSlot.FEET);
        ItemStack itemStackMainhand = player.getHeldItemMainhand();
        EntityIronicZombie zombie = (EntityIronicZombie) event.getSource().getTrueSource();
        //check head
        if (!head.isEmpty() && randCheck()) {
            ItemStack stack1 = getConversion(head, zombie);
            if (stack1 != head) {
                player.setItemStackToSlot(EntityEquipmentSlot.HEAD, ItemStack.EMPTY);
                player.setItemStackToSlot(EntityEquipmentSlot.HEAD, getConversion(head, zombie));
                event.getSource().getTrueSource().getEntityData().setInteger("transfers", event.getSource().getTrueSource().getEntityData().getInteger("transfers") - 1);
            }
        }
        //check chest
        if (!chest.isEmpty() && randCheck()) {
            ItemStack stack1 = getConversion(chest, zombie);
            if (stack1 != chest) {
                player.setItemStackToSlot(EntityEquipmentSlot.CHEST, ItemStack.EMPTY);
                player.setItemStackToSlot(EntityEquipmentSlot.CHEST, getConversion(chest, zombie));
                event.getSource().getTrueSource().getEntityData().setInteger("transfers", event.getSource().getTrueSource().getEntityData().getInteger("transfers") - 1);
            }
        }
        //check legs
        if (!legs.isEmpty() && randCheck()) {
            ItemStack stack1 = getConversion(legs, zombie);
            if (stack1 != legs) {
                player.setItemStackToSlot(EntityEquipmentSlot.LEGS, ItemStack.EMPTY);
                player.setItemStackToSlot(EntityEquipmentSlot.LEGS, getConversion(legs, zombie));
                event.getSource().getTrueSource().getEntityData().setInteger("transfers", event.getSource().getTrueSource().getEntityData().getInteger("transfers") - 1);
            }
        }

        //check feet
        if (!head.isEmpty() && randCheck()) {
            ItemStack stack1 = getConversion(feet, zombie);
            if (stack1 != feet) {
                player.setItemStackToSlot(EntityEquipmentSlot.FEET, ItemStack.EMPTY);
                player.setItemStackToSlot(EntityEquipmentSlot.FEET, getConversion(feet, zombie));
                event.getSource().getTrueSource().getEntityData().setInteger("transfers", event.getSource().getTrueSource().getEntityData().getInteger("transfers") - 1);
            }
        }
        //check held item
        if (!itemStackMainhand.isEmpty() && randCheck()) {
            ItemStack stack1 = getConversion(itemStackMainhand, zombie);
            if (stack1 != itemStackMainhand) {
                player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
                player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, getConversion(itemStackMainhand, zombie));
                event.getSource().getTrueSource().getEntityData().setInteger("transfers", event.getSource().getTrueSource().getEntityData().getInteger("transfers") - 1);
            }
        }

        if (event.getSource().getTrueSource().getEntityData().getInteger("transfers") <= 0)
            ((EntityIronicZombie) event.getSource().getTrueSource()).clearActivePotions();
    }

    public static boolean randCheck() {
        return Math.random() < CONVERSION_CHANCE;
    }

    public static ItemStack getConversion(ItemStack stack, EntityIronicZombie zombie) {
        String type = zombie.getEntityData().getString("type");
        Pair<Item, String> pair = new Pair<>(stack.getItem(), type);
        if (!conversionItems.containsKey(pair)) {
            System.out.println("Item is not eligible for conversion of any kind!");
            return stack;
        }
        NBTTagCompound nbt = stack.getTagCompound();
        ItemStack newstack = new ItemStack(conversionItems.get(pair));
        newstack.setCount(stack.getCount());
        newstack.setTagCompound(nbt);
        newstack.setItemDamage(transformDamage(stack, newstack));
        System.out.println("success!");
        return newstack;
    }

    public static int transformDamage(ItemStack oldStack, ItemStack newStack) {
        int damage1 = oldStack.getItemDamage();
        int maxdamage1 = oldStack.getMaxDamage();
        int maxdamage2 = newStack.getMaxDamage();
        if (maxdamage1 != 0) return damage1 * maxdamage2 / maxdamage1;
        return 0;
    }
}