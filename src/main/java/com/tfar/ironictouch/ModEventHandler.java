package com.tfar.ironictouch;

import com.tfar.ironictouch.util.ReferenceVariables;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.tfar.ironictouch.util.ModConfig.CONVERSION_CHANCE;
import static com.tfar.ironictouch.util.ModConfig.CONVERSION_TYPE;
import static com.tfar.ironictouch.util.ReferenceVariables.conversionItems;

@Mod.EventBusSubscriber(modid= ReferenceVariables.MOD_ID)
public class ModEventHandler {
    public ModEventHandler() {
    }
    @SubscribeEvent
    public static void onMobAttack(LivingHurtEvent event) {
        //null check
        if (event.getSource() == null)return;
        //is it an ironic zombie and is the player being attacked?
        if (!(event.getSource().getTrueSource() instanceof EntityIronicZombie) || !(event.getEntityLiving() instanceof EntityPlayer)) {
            return;
        }
        if (event.getSource().getTrueSource().getEntityData().getInteger("transfers")<=0 || ((EntityIronicZombie) event.getSource().getTrueSource()).getActivePotionEffect(MobEffects.GLOWING)== null)return;
        EntityPlayer player = (EntityPlayer) event.getEntityLiving();
        ItemStack head = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
        ItemStack chest = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
        ItemStack legs = player.getItemStackFromSlot(EntityEquipmentSlot.LEGS);
        ItemStack feet = player.getItemStackFromSlot(EntityEquipmentSlot.FEET);
        ItemStack itemStackMainhand = player.getHeldItemMainhand();
        //check head
        if (!head.isEmpty() &&randCheck()){
            ItemStack stack1 = getConversion(head);
            if (stack1 != head){
            player.setItemStackToSlot(EntityEquipmentSlot.HEAD, ItemStack.EMPTY);
            player.setItemStackToSlot(EntityEquipmentSlot.HEAD, getConversion(head));
            event.getSource().getTrueSource().getEntityData().setInteger("transfers",event.getSource().getTrueSource().getEntityData().getInteger("transfers")-1);}
        }
        //check chest
        if (!chest.isEmpty() &&randCheck()){
            ItemStack stack1 = getConversion(chest);
            if (stack1 != chest){
                player.setItemStackToSlot(EntityEquipmentSlot.CHEST, ItemStack.EMPTY);
                player.setItemStackToSlot(EntityEquipmentSlot.CHEST, getConversion(chest));
                event.getSource().getTrueSource().getEntityData().setInteger("transfers",event.getSource().getTrueSource().getEntityData().getInteger("transfers")-1);}
        }
        //check legs
        if (!legs.isEmpty() &&randCheck()){
            ItemStack stack1 = getConversion(legs);
            if (stack1 != legs){
                player.setItemStackToSlot(EntityEquipmentSlot.LEGS, ItemStack.EMPTY);
                player.setItemStackToSlot(EntityEquipmentSlot.LEGS, getConversion(legs));
                event.getSource().getTrueSource().getEntityData().setInteger("transfers",event.getSource().getTrueSource().getEntityData().getInteger("transfers")-1);}
        }

        //check feet
        if (!head.isEmpty() &&randCheck()){
            ItemStack stack1 = getConversion(feet);
            if (stack1 != feet){
                player.setItemStackToSlot(EntityEquipmentSlot.FEET, ItemStack.EMPTY);
                player.setItemStackToSlot(EntityEquipmentSlot.FEET, getConversion(feet));
                event.getSource().getTrueSource().getEntityData().setInteger("transfers",event.getSource().getTrueSource().getEntityData().getInteger("transfers")-1);}
        }
        //check held item
        if (!itemStackMainhand.isEmpty() && (itemStackMainhand.getItem() instanceof ItemSword || itemStackMainhand.getItem() instanceof ItemAxe || itemStackMainhand.getItem() instanceof ItemSpade || itemStackMainhand.getItem() instanceof ItemPickaxe && randCheck())){
            ItemStack stack1 = getConversion(itemStackMainhand);
            if (stack1 != itemStackMainhand){
            player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
           player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, getConversion(itemStackMainhand));
            event.getSource().getTrueSource().getEntityData().setInteger("transfers",event.getSource().getTrueSource().getEntityData().getInteger("transfers")-1);}
        }
        if (event.getSource().getTrueSource().getEntityData().getInteger("transfers")<=0)((EntityIronicZombie) event.getSource().getTrueSource()).clearActivePotions();
    }
    public static boolean randCheck(){
        return Math.random()< CONVERSION_CHANCE;
    }

    public static ItemStack getConversion(ItemStack stack) {
        String type = CONVERSION_TYPE;
        switch (type) {
            case "gold":{
                if (conversionItems.containsKey(stack.getItem()))
                if (conversionItems.get(stack.getItem()).equals("gold")) return stack;
                return getGold(stack);}
            case "iron":{
                if (conversionItems.containsKey(stack.getItem()))
                    if (conversionItems.get(stack.getItem()).equals("iron")) return stack;
                return getIron(stack);}
            case "diamond":{
                if (conversionItems.containsKey(stack.getItem()))
                    if (conversionItems.get(stack.getItem()).equals("diamond")) return stack;
                return getDiamond(stack);}
            }
    return stack;}

    public static ItemStack getIron(ItemStack stack) {
        NBTTagCompound nbt = stack.getTagCompound();
        if (stack.getItem() instanceof ItemArmor) {
            EntityEquipmentSlot slot = ((ItemArmor) stack.getItem()).getEquipmentSlot();
            switch (slot) {
                case HEAD:{
                    ItemStack newstack = new ItemStack(Items.IRON_HELMET);
                    newstack.setTagCompound(nbt);
                    newstack.setItemDamage(transformDamage(stack,newstack));
                    return newstack;
                }
                case CHEST:
                {
                    ItemStack newstack = new ItemStack(Items.IRON_CHESTPLATE);
                    newstack.setTagCompound(nbt);
                    newstack.setItemDamage(transformDamage(stack,newstack));
                    return newstack;
                }
                case LEGS:
                {
                    ItemStack newstack = new ItemStack(Items.IRON_LEGGINGS);
                    newstack.setTagCompound(nbt);
                    newstack.setItemDamage(transformDamage(stack,newstack));
                    return newstack;
                }
                case FEET:
                {
                    ItemStack newstack = new ItemStack(Items.IRON_BOOTS);
                    newstack.setTagCompound(nbt);
                    newstack.setItemDamage(transformDamage(stack,newstack));
                    return newstack;
                }
            }
        }
        if(stack.getItem()instanceof ItemSword) {
            ItemStack newstack = new ItemStack(Items.IRON_SWORD);
            newstack.setTagCompound(nbt);
            newstack.setItemDamage(transformDamage(stack,newstack));
            return newstack;
        }
        if(stack.getItem()instanceof ItemAxe) {
            ItemStack newstack = new ItemStack(Items.IRON_AXE);
            newstack.setTagCompound(nbt);
            newstack.setItemDamage(transformDamage(stack,newstack));
            return newstack;
        }
        if(stack.getItem()instanceof ItemSpade) {
            ItemStack newstack = new ItemStack(Items.IRON_SHOVEL);
            newstack.setTagCompound(nbt);
            newstack.setItemDamage(transformDamage(stack,newstack));
            return newstack;
        }
        if(stack.getItem()instanceof ItemPickaxe) {
            ItemStack newstack = new ItemStack(Items.IRON_PICKAXE);
            newstack.setTagCompound(nbt);
            newstack.setItemDamage(transformDamage(stack,newstack));
            return newstack;
        }
        return stack;
     }

    public static ItemStack getGold(ItemStack stack) {
        NBTTagCompound nbt = stack.getTagCompound();
        if (stack.getItem() instanceof ItemArmor) {
            EntityEquipmentSlot slot = ((ItemArmor) stack.getItem()).getEquipmentSlot();
            switch (slot) {
                case HEAD:{
                    ItemStack newstack = new ItemStack(Items.GOLDEN_HELMET);
                    newstack.setTagCompound(nbt);
                    newstack.setItemDamage(transformDamage(stack,newstack));
                    return newstack;
                }
                case CHEST:
                {
                    ItemStack newstack = new ItemStack(Items.GOLDEN_CHESTPLATE);
                    newstack.setTagCompound(nbt);
                    newstack.setItemDamage(transformDamage(stack,newstack));
                    return newstack;
                }
                case LEGS:
                {
                    ItemStack newstack = new ItemStack(Items.GOLDEN_LEGGINGS);
                    newstack.setTagCompound(nbt);
                    newstack.setItemDamage(transformDamage(stack,newstack));
                    return newstack;
                }
                case FEET:
                {
                    ItemStack newstack = new ItemStack(Items.GOLDEN_BOOTS);
                    newstack.setTagCompound(nbt);
                    newstack.setItemDamage(transformDamage(stack,newstack));
                    return newstack;
                }
            }
        }
        if(stack.getItem()instanceof ItemSword) {
            ItemStack newstack = new ItemStack(Items.GOLDEN_SWORD);
            newstack.setTagCompound(nbt);
            newstack.setItemDamage(transformDamage(stack,newstack));
            return newstack;
        }
        if(stack.getItem()instanceof ItemAxe) {
            ItemStack newstack = new ItemStack(Items.GOLDEN_AXE);
            newstack.setTagCompound(nbt);
            newstack.setItemDamage(transformDamage(stack,newstack));
            return newstack;
        }
        if(stack.getItem()instanceof ItemSpade) {
            ItemStack newstack = new ItemStack(Items.GOLDEN_SHOVEL);
            newstack.setTagCompound(nbt);
            newstack.setItemDamage(transformDamage(stack,newstack));
            return newstack;
        }
        if(stack.getItem()instanceof ItemPickaxe) {
            ItemStack newstack = new ItemStack(Items.GOLDEN_PICKAXE);
            newstack.setTagCompound(nbt);
            newstack.setItemDamage(transformDamage(stack,newstack));
            return newstack;
        }
        return stack;
    }

    public static ItemStack getDiamond(ItemStack stack) {
        NBTTagCompound nbt = stack.getTagCompound();
        if (stack.getItem() instanceof ItemArmor) {
            EntityEquipmentSlot slot = ((ItemArmor) stack.getItem()).getEquipmentSlot();
            switch (slot) {
                case HEAD:{
                    ItemStack newstack = new ItemStack(Items.DIAMOND_HELMET);
                    newstack.setTagCompound(nbt);
                    newstack.setItemDamage(transformDamage(stack,newstack));
                    return newstack;
                }
                case CHEST:
                {
                    ItemStack newstack = new ItemStack(Items.DIAMOND_CHESTPLATE);
                    newstack.setTagCompound(nbt);
                    newstack.setItemDamage(transformDamage(stack,newstack));
                    return newstack;
                }
                case LEGS:
                {
                    ItemStack newstack = new ItemStack(Items.DIAMOND_LEGGINGS);
                    newstack.setTagCompound(nbt);
                    newstack.setItemDamage(transformDamage(stack,newstack));
                    return newstack;
                }
                case FEET:
                {
                    ItemStack newstack = new ItemStack(Items.DIAMOND_BOOTS);
                    newstack.setTagCompound(nbt);
                    newstack.setItemDamage(transformDamage(stack,newstack));
                    return newstack;
                }
            }
        }
        if(stack.getItem()instanceof ItemSword) {
            ItemStack newstack = new ItemStack(Items.DIAMOND_SWORD);
            newstack.setItemDamage(transformDamage(stack,newstack));
            newstack.setTagCompound(nbt);
            return newstack;
        }
        if(stack.getItem()instanceof ItemAxe) {
            ItemStack newstack = new ItemStack(Items.DIAMOND_AXE);
            newstack.setItemDamage(transformDamage(stack,newstack));
            newstack.setTagCompound(nbt);
            return newstack;
        }
        if(stack.getItem()instanceof ItemSpade) {
            ItemStack newstack = new ItemStack(Items.DIAMOND_SHOVEL);
            newstack.setItemDamage(transformDamage(stack,newstack));
            newstack.setTagCompound(nbt);
            return newstack;
        }
        if(stack.getItem()instanceof ItemPickaxe) {
            ItemStack newstack = new ItemStack(Items.DIAMOND_PICKAXE);
            newstack.setItemDamage(transformDamage(stack,newstack));
            newstack.setTagCompound(nbt);
            return newstack;
        }
        return stack;
    }
    public static int transformDamage(ItemStack oldStack, ItemStack newStack){
        int damage1 = oldStack.getItemDamage();
        int maxdamage1 = oldStack.getMaxDamage();
        int maxdamage2 = newStack.getMaxDamage();
        return damage1*maxdamage2/maxdamage1;
    }
}