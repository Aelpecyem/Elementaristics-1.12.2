package de.aelpecyem.elementaristics.entity.render.elemental;

import de.aelpecyem.elementaristics.entity.elementals.EntityAetherElemental;
import de.aelpecyem.elementaristics.entity.model.ModelAetherElemental;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderElementalAether extends RenderLiving<EntityAetherElemental> {
    public RenderElementalAether(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelAetherElemental(), 0);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityAetherElemental entity) {
        Minecraft mc = Minecraft.getMinecraft();
        if (!(mc.getRenderViewEntity() instanceof AbstractClientPlayer))
            return DefaultPlayerSkin.getDefaultSkinLegacy();

        return ((AbstractClientPlayer) mc.getRenderViewEntity()).getLocationSkin();
    }

}
