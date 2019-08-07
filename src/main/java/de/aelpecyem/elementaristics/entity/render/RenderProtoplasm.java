package de.aelpecyem.elementaristics.entity.render;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.entity.EntityCultist;
import de.aelpecyem.elementaristics.entity.EntityProtoplasm;
import de.aelpecyem.elementaristics.entity.model.ModelCultist;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSlime;
import net.minecraft.client.renderer.entity.layers.LayerSlimeGel;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderProtoplasm extends RenderLiving<EntityProtoplasm> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(Elementaristics.MODID, "textures/entity/protoplasm.png");

    public RenderProtoplasm(RenderManager rendermanagerIn) {
        //RenderSlime
        super(rendermanagerIn, new ModelSlime(16), 0.25F);
        this.addLayer(new LayerProtoplasmGel(this));
    }

    /**
     * Renders the desired {@code T} type Entity.
     */
    public void doRender(EntityProtoplasm entity, double x, double y, double z, float entityYaw, float partialTicks) {
        this.shadowSize = 0.25F * (float)entity.getSize();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    /**
     * Allows the render to do state modifications necessary before the model is rendered.
     */
    protected void preRenderCallback(EntityProtoplasm entitylivingbaseIn, float partialTickTime) {
        GlStateManager.scale(0.999F, 0.999F, 0.999F);
        float f1 = (float)entitylivingbaseIn.getSize();
        float f3 = 1;
        GlStateManager.scale(f3 * f1, 1.0F / f3 * f1, f3 * f1);
    }


    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityProtoplasm entity) {
        return TEXTURE;
    }

}
