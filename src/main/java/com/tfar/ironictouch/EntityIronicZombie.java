package com.tfar.ironictouch;

import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import static com.tfar.ironictouch.util.ModConfig.MAX_TRANSFERS;
import static com.tfar.ironictouch.util.ModConfig.TIME_LIMIT;

public class EntityIronicZombie extends EntityZombie {

    public EntityIronicZombie(World worldIn) {
        super(worldIn);
    }
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata){
        this.getEntityData().setInteger("transfers",MAX_TRANSFERS);
        this.addPotionEffect(new PotionEffect(MobEffects.GLOWING, TIME_LIMIT*20, 1));
        return livingdata;
    }
   /* @Override
    public boolean attackEntityAsMob(Entity entity){
        return false;
    }*/
}
