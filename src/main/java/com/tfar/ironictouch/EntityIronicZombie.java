package com.tfar.ironictouch;

import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import static com.tfar.ironictouch.util.ModConfig.*;
import static com.tfar.ironictouch.util.ReferenceVariables.uniqueTypes;
import static com.tfar.ironictouch.util.ReferenceVariables.uniqueTypesList;
import static java.lang.Math.floor;

public class EntityIronicZombie extends EntityZombie {

    public EntityIronicZombie(World worldIn) {
        super(worldIn);
    }
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata){

        String type = uniqueTypesList.get((int)floor(Math.random()*uniqueTypes));
        this.getEntityData().setInteger("transfers",MAX_TRANSFERS);
        this.getEntityData().setString("type",type);
        this.addPotionEffect(new PotionEffect(MobEffects.GLOWING, TIME_LIMIT*20, 1));
        this.setCustomNameTag(this.getEntityData().getString("type"));
        this.setAlwaysRenderNameTag(true);
        return livingdata;
    }
   /* @Override
    public boolean attackEntityAsMob(Entity entity){
        return false;
    }*/
}
