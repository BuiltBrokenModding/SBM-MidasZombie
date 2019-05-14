package com.builtbroken.midaszombie.entity;

import com.builtbroken.midaszombie.ConfigMain;
import com.builtbroken.midaszombie.materials.MaterialRegistry;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import static com.builtbroken.midaszombie.ConfigMain.MAX_TRANSFERS;
import static com.builtbroken.midaszombie.ConfigMain.TIME_LIMIT;

public class EntityIronicZombie extends EntityZombie
{
    public static final String NBT_TRANSFERS = "transfers";
    public static final String NBT_TYPE = "type";

    private static final DataParameter<ResourceLocation> MATERIAL_TYPE_DP = EntityDataManager.<ResourceLocation>createKey(EntityIronicZombie.class, DataSerializerResourceLocation.INSTANCE);
    private static final DataParameter<Integer> TRANSFERS_LEFT_DP = EntityDataManager.<Integer>createKey(EntityIronicZombie.class, DataSerializers.VARINT);

    public EntityIronicZombie(World worldIn)
    {
        super(worldIn);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.getDataManager().register(MATERIAL_TYPE_DP, MaterialRegistry.GOLD_TYPE);
        this.getDataManager().register(TRANSFERS_LEFT_DP, 1);
    }

    @Override
    public void setItemStackToSlot(EntityEquipmentSlot slotIn, ItemStack stack)
    {
        //We don't want armor set on this zombie type
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
        if (getMaterialType() != null)
        {
            this.setCustomNameTag(getMaterialType().toString());
            this.setAlwaysRenderNameTag(true);
        }

        return livingdata;
    }

    @Override
    public String getCustomNameTag()
    {
        String name = super.getCustomNameTag();
        String translateKey = "zombie.midas." + name + ".tag.name";
        if (I18n.hasKey(translateKey))
        {
            return I18n.format(translateKey);
        }
        return name;
    }

    public boolean hasMidasTouch()
    {
        return getActivePotionEffect(MobEffects.GLOWING) != null;
    }

    public int getTransfersRemaining()
    {
        return this.getDataManager().get(TRANSFERS_LEFT_DP);
    }

    public void setTransfersRemaining(int value)
    {
        this.getDataManager().set(TRANSFERS_LEFT_DP, value);
    }

    public void consumeTransfer(int count)
    {
        if(MAX_TRANSFERS != -1)
        {
            final int transfersLeft = getTransfersRemaining() - count;
            setTransfersRemaining(transfersLeft);

            if (transfersLeft <= 0)
            {
                clearActivePotions();
            }
        }
    }

    public ResourceLocation getMaterialType()
    {
        return this.getDataManager().get(MATERIAL_TYPE_DP);
    }

    public void setMaterialType(ResourceLocation type)
    {
        this.getDataManager().set(MATERIAL_TYPE_DP, type);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        if (getMaterialType() != null)
        {
            compound.setString(NBT_TYPE, getMaterialType().toString());
        }
        compound.setInteger(NBT_TRANSFERS, getTransfersRemaining());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        if (compound.hasKey(NBT_TYPE))
        {
            setMaterialType(new ResourceLocation(compound.getString(NBT_TYPE)));
        }
        setTransfersRemaining(compound.getInteger(NBT_TRANSFERS));
    }
}
