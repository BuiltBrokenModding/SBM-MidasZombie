package com.builtbroken.midaszombie.entity;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.ByteBufUtils;

/**
 * Created by Dark(DarkGuardsman, Robert) on 5/10/2019.
 */
public class DataSerializerResourceLocation implements DataSerializer<ResourceLocation>
{
    public static final DataSerializerResourceLocation INSTANCE = new DataSerializerResourceLocation();

    @Override
    public void write(PacketBuffer buf, ResourceLocation value)
    {
        ByteBufUtils.writeUTF8String(buf, value.toString());
    }

    @Override
    public ResourceLocation read(PacketBuffer buf)
    {
        return new ResourceLocation(ByteBufUtils.readUTF8String(buf));
    }

    @Override
    public DataParameter<ResourceLocation> createKey(int id)
    {
        return new DataParameter<ResourceLocation>(id, this);
    }

    @Override
    public ResourceLocation copyValue(ResourceLocation value)
    {
        return new ResourceLocation(value.getNamespace(), value.getPath());
    }
}
