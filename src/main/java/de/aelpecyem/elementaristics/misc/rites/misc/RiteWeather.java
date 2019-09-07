package de.aelpecyem.elementaristics.misc.rites.misc;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.entity.nexus.EntityDimensionalNexus;
import de.aelpecyem.elementaristics.init.ModItems;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.rites.RiteBase;
import de.aelpecyem.elementaristics.particles.ParticleGeneric;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class RiteWeather extends RiteBase {

    public RiteWeather() {
        super(new ResourceLocation(Elementaristics.MODID, "rite_weather"), 220, 0.8F, 6, Aspects.water, Aspects.air);
    }

    @Override
    public void doMagic(EntityDimensionalNexus nexus) {
        List<EntityItem> targets = nexus.world.getEntitiesWithinAABB(EntityItem.class, nexus.getEntityBoundingBox().grow(4));

        for (EntityItem item : targets) {
            if (item.getItem().getItem() == ModItems.lightning_tangible) {
                item.getItem().shrink(1);
                nexus.world.getWorldInfo().setThundering(true);
                nexus.world.getWorldInfo().setRaining(true);
                break;
            }
            if (item.getItem().getItem() == ModItems.sparks_brightest) {
                item.getItem().shrink(1);
                nexus.world.getWorldInfo().setRaining(false);
                break;
            }
            if (item.getItem().getItem() == ModItems.flesh_lamb) {
                item.getItem().shrink(1);
                nexus.world.getWorldInfo().setRaining(true);
                break;
            }
        }

    }

    @Override
    public void onRitual(EntityDimensionalNexus nexus) {
        List<Entity> entities = nexus.world.getEntitiesWithinAABB(EntityItem.class, nexus.getEntityBoundingBox().grow(4));
        for (Entity entity : entities) {
            nexus.suckInEntity(entity, entity.height / 2);
            Elementaristics.proxy.generateGenericParticles(entity, Aspects.order.getColor(), 0.5F, 60, 0, false, false);
        }
        if (nexus.world.isRemote) {
            Elementaristics.proxy.generateGenericParticles(new ParticleGeneric(nexus.world, nexus.posX + nexus.world.rand.nextGaussian(), nexus.posY + nexus.world.rand.nextGaussian(), nexus.posZ + nexus.world.rand.nextGaussian(), 0, 0, 0, Aspects.air.getColor(), 1, 200, 0, false, true, true, true, nexus.posX, nexus.posY + 0.5, nexus.posZ));
            Elementaristics.proxy.generateGenericParticles(nexus.world, nexus.posX, nexus.posY + 0.5F, nexus.posZ, Aspects.water.getColor(), 1, 80, 0, false, false);
        }
    }
}
