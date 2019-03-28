package de.aelpecyem.elementaristics.entity.render;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.entity.EntityCultist;
import de.aelpecyem.elementaristics.entity.EntitySilverThread;
import de.aelpecyem.elementaristics.entity.model.ModelCultist;
import de.aelpecyem.elementaristics.entity.model.ModelSilverThread;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderCultist extends RenderLiving<EntityCultist>{
    public static final ResourceLocation TEXTURE = new ResourceLocation(Elementaristics.MODID, "textures/entity/cultist.png");
    public RenderCultist(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelCultist(), 0.5F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityCultist entity) {
        return TEXTURE;
    }

}
