package com.tfar.ironictouch;

import com.tfar.ironictouch.util.ReferenceVariables;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.tfar.ironictouch.util.ModConfig.CONVERSION_CHANCE;

@Mod.EventBusSubscriber(modid= ReferenceVariables.MOD_ID)
public class ModEventHandler {
    public ModEventHandler() {
    }
    @SubscribeEvent
    public static void onMobAttack(LivingHurtEvent event) {
        if (!(event.getSource().getTrueSource() instanceof EntityIronicZombie) || !(event.getEntityLiving() instanceof EntityPlayer)) {
            return;
        }

        if (event.getSource().getTrueSource().getEntityData().getInteger("transfers")<=0 || !event.getSource().getTrueSource().isGlowing())return;
        EntityPlayer player = (EntityPlayer) event.getEntityLiving();
        ItemStack head = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
        ItemStack chest = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
        ItemStack legs = player.getItemStackFromSlot(EntityEquipmentSlot.LEGS);
        ItemStack feet = player.getItemStackFromSlot(EntityEquipmentSlot.FEET);
        ItemStack stack = player.getHeldItemMainhand();

        if (head != ItemStack.EMPTY && !head.equals(new ItemStack(Items.IRON_HELMET))&&randCheck()){
            player.setItemStackToSlot(EntityEquipmentSlot.HEAD, ItemStack.EMPTY);
            player.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
            event.getSource().getTrueSource().getEntityData().setInteger("transfers",event.getSource().getTrueSource().getEntityData().getInteger("transfers")-1);
        }
        if (chest != ItemStack.EMPTY && !chest.equals(new ItemStack(Items.IRON_CHESTPLATE))&&randCheck()){
            player.setItemStackToSlot(EntityEquipmentSlot.CHEST, ItemStack.EMPTY);
            player.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
            event.getSource().getTrueSource().getEntityData().setInteger("transfers",event.getSource().getTrueSource().getEntityData().getInteger("transfers")-1);
        }
        if (legs != ItemStack.EMPTY && !legs.equals(new ItemStack(Items.IRON_LEGGINGS))&&randCheck()){
            player.setItemStackToSlot(EntityEquipmentSlot.LEGS, ItemStack.EMPTY);
            player.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(Items.IRON_LEGGINGS));
            event.getSource().getTrueSource().getEntityData().setInteger("transfers",event.getSource().getTrueSource().getEntityData().getInteger("transfers")-1);
        }
        if (feet != ItemStack.EMPTY && !feet.equals(new ItemStack(Items.IRON_BOOTS))&&randCheck()){
            player.setItemStackToSlot(EntityEquipmentSlot.FEET, ItemStack.EMPTY);
            player.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(Items.IRON_BOOTS));
            event.getSource().getTrueSource().getEntityData().setInteger("transfers",event.getSource().getTrueSource().getEntityData().getInteger("transfers")-1);
        }
        if (stack != ItemStack.EMPTY && (stack.getItem() instanceof ItemSword || stack.getItem() instanceof ItemAxe) && !stack.equals(new ItemStack(Items.IRON_SWORD)) && !stack.equals(new ItemStack(Items.IRON_AXE))  &&randCheck()){
            int flag = 0;
            Item item = stack.getItem();
            if (item instanceof ItemSword) flag=1;
            if (item instanceof ItemAxe) flag=2;
            player.setItemStackToSlot(EntityEquipmentSlot.FEET, ItemStack.EMPTY);
            if (flag==1)player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
            if (flag==2)player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_AXE));
            event.getSource().getTrueSource().getEntityData().setInteger("transfers",event.getSource().getTrueSource().getEntityData().getInteger("transfers")-1);
        }
        if (event.getSource().getTrueSource().getEntityData().getInteger("transfers")<=0)event.getSource().getTrueSource().setGlowing(false);
    }
    public static boolean randCheck(){
        return Math.random()< CONVERSION_CHANCE;
    }
}