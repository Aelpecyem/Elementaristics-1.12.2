package de.aelpecyem.elementaristics.entity.render;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.config.Config;
import de.aelpecyem.elementaristics.entity.EntityCultist;
import de.aelpecyem.elementaristics.entity.model.ModelCultist;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderCultist extends RenderLiving<EntityCultist>{
    public RenderCultist(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelCultist(), 0.5F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityCultist entity) {
        return Config.client.cultistTextures ? getAvailableTextures(entity) : new ResourceLocation(Elementaristics.MODID, "textures/entity/cultists/cultist_1.png");
    }

    @Override
    public void bindTexture(ResourceLocation location) {
        if (renderManager.renderEngine.getTexture(location) instanceof DynamicTexture) {
            location = new ResourceLocation(Elementaristics.MODID, "textures/entity/cultists/cultist_1.png");
        }
        super.bindTexture(location);
    }

    public ResourceLocation getAvailableTextures(EntityCultist entity) {
        return new ResourceLocation(Elementaristics.MODID, "textures/entity/cultists/cultist_" + entity.getAspect().getName() + ".png");
    }
}
