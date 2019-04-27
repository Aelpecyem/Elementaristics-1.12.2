package de.aelpecyem.elementaristics.entity.render;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.entity.EntityCultist;
import de.aelpecyem.elementaristics.entity.EntityProtoplasm;
import de.aelpecyem.elementaristics.entity.model.ModelCultist;
import de.aelpecyem.elementaristics.entity.model.ModelProtoplasm;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderProtoplasm extends RenderLiving<EntityProtoplasm> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(Elementaristics.MODID, "textures/entity/protoplasm.png");

    public RenderProtoplasm(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelProtoplasm(), 0.5F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityProtoplasm entity) {
        return TEXTURE;
    }

}
