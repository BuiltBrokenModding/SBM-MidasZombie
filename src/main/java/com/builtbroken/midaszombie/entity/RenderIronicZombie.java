package com.builtbroken.midaszombie.entity;

import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;

/**
 * Created by Dark(DarkGuardsman, Robert) on 5/10/2019.
 */
public class RenderIronicZombie extends RenderBiped<EntityIronicZombie>
{
    public final static HashMap<ResourceLocation, ResourceLocation> typeToTexture = new HashMap();

    private static final ResourceLocation DEFAULT_TEXTURE = new ResourceLocation("textures/entity/zombie/zombie.png");

    public RenderIronicZombie(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelZombie(), 0.5F);
        LayerBipedArmor layerbipedarmor = new LayerBipedArmor(this)
        {
            protected void initArmor()
            {
                this.modelLeggings = new ModelZombie(0.5F, true);
                this.modelArmor = new ModelZombie(1.0F, true);
            }
        };
        this.addLayer(layerbipedarmor);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityIronicZombie entity)
    {
        final ResourceLocation type = entity.getMaterialType();
        if (type != null)
        {
            final ResourceLocation texture = typeToTexture.get(type);
            if (texture != null)
            {
                return texture;
            }
        }
        return DEFAULT_TEXTURE;
    }
}
