package de.aelpecyem.elementaristics.entity.render;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.particles.ParticleGeneric;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;


public class RenderSpellProjectile extends Render {
    public RenderSpellProjectile(RenderManager manager) {
        super(manager);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity) {
        return null;
    }


}
