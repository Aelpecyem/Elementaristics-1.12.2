package de.aelpecyem.elementaristics.entity.render;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.entity.EntityCultist;
import de.aelpecyem.elementaristics.entity.EntitySilverThread;
import de.aelpecyem.elementaristics.entity.model.ModelCultist;
import de.aelpecyem.elementaristics.entity.model.ModelSilverThread;
import de.aelpecyem.elementaristics.misc.elements.Aspect;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RenderCultist extends RenderLiving<EntityCultist>{
    //public static final ResourceLocation TEXTURE = new ResourceLocation(Elementaristics.MODID, "textures/entity/cultist.png");
    public RenderCultist(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelCultist(), 0.5F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityCultist entity) {
        return new ResourceLocation(Elementaristics.MODID, "textures/entity/cultists/" + getAvailableTextures(entity)+ ".png");
    }

    public String getAvailableTextures(EntityCultist entity){
        List<Aspect> availableTex = new ArrayList<>();
        availableTex.add(Aspects.earth);
        availableTex.add(Aspects.fire);
        availableTex.add(Aspects.light);
        availableTex.add(Aspects.soul);
        availableTex.add(Aspects.vacuum);
        availableTex.add(Aspects.electricity);
        availableTex.add(Aspects.body);
        if (availableTex.contains(entity.getAspect())){
            return "cultist_" + entity.getAspect().getName();
        }
        return "cultist_1";
    }

}
