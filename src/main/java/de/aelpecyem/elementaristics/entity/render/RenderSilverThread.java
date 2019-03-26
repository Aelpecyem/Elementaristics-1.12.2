package de.aelpecyem.elementaristics.entity.render;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.entity.EntitySilverThread;
import de.aelpecyem.elementaristics.entity.model.ModelSilverThread;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderSilverThread extends RenderLiving<EntitySilverThread>{
    public static final ResourceLocation TEXTURE = new ResourceLocation(Elementaristics.MODID, "textures/entity/silver_thread.png");
    public RenderSilverThread(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelSilverThread(), 0.5F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntitySilverThread entity) {
        return TEXTURE;
    }

}
