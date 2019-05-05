package com.builtbroken.midaszombie.entity;

import com.builtbroken.midaszombie.MidasZombie;
import com.builtbroken.midaszombie.materials.MaterialRegistry;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import static com.builtbroken.midaszombie.ModConfig.MAX_TRANSFERS;
import static com.builtbroken.midaszombie.ModConfig.TIME_LIMIT;

public class EntityIronicZombie extends EntityZombie
{
    public static final String NBT_DATA = MidasZombie.MOD_ID + ":data";
    public static final String NBT_TRANSFERS = "transfers";
    public static final String NBT_TYPE = "type";

    public EntityIronicZombie(World worldIn)
    {
        super(worldIn);
    }

    @Override
    protected void applyEntityAI()
    {
        super.applyEntityAI();
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityZombie.class, true));
    }

    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        //Setup data
        setTransfersRemaining(MAX_TRANSFERS); //TODO random
        setMaterialType(MaterialRegistry.getRandomMaterial());

        //Setup potion as timer
        this.addPotionEffect(new PotionEffect(MobEffects.GLOWING, TIME_LIMIT * 20, 1));

        //Setup name
        //this.setCustomNameTag(getMaterialType().toString());
        //this.setAlwaysRenderNameTag(true);

        return livingdata;
    }

    public boolean hasMidasTouch()
    {
        return getActivePotionEffect(MobEffects.GLOWING) != null;
    }

    public int getTransfersRemaining()
    {
        return getMidasSaveData().getInteger(NBT_TRANSFERS);
    }

    public void setTransfersRemaining(int value)
    {
        getMidasSaveData().setInteger(NBT_TRANSFERS, value);
    }

    public void consumeTransfer(int count)
    {
        final int transfersLeft = getTransfersRemaining() - count;
        setTransfersRemaining(transfersLeft);

        if (transfersLeft <= 0)
        {
            clearActivePotions();
        }
    }

    public ResourceLocation getMaterialType()
    {
        final String value = getMidasSaveData().getString(NBT_TYPE).trim();
        if (!value.isEmpty())
        {
            return new ResourceLocation(value);
        }
        return null;
    }

    public void setMaterialType(ResourceLocation type)
    {
        getMidasSaveData().setString(NBT_TYPE, type.toString());
    }

    public NBTTagCompound getMidasSaveData()
    {
        if (getEntityData().hasKey(NBT_DATA))
        {
            getEntityData().setTag(NBT_DATA, new NBTTagCompound());
        }
        return getEntityData().getCompoundTag(NBT_DATA);
    }
}
