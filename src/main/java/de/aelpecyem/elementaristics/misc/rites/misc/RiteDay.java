package de.aelpecyem.elementaristics.misc.rites.misc;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.entity.nexus.EntityDimensionalNexus;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.rites.RiteBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class RiteDay extends RiteBase {

    public RiteDay() {
        super(new ResourceLocation(Elementaristics.MODID, "rite_day"), 400, 0.5F, 12, Aspects.light, Aspects.fire);
    }

    @Override
    public void doMagic(EntityDimensionalNexus nexus) {
        List<EntitySheep> targets = nexus.world.getEntitiesWithinAABB(EntitySheep.class, nexus.getEntityBoundingBox().grow(4));
        boolean flag = false;
        for (EntitySheep sheepAffected : targets) {
            if (flag)
                break;
            if (sheepAffected.getFleeceColor() == EnumDyeColor.WHITE) {
                sheepAffected.setDropItemsWhenDead(false);
                sheepAffected.attackEntityFrom(DamageSource.GENERIC, 20);
                flag = true;
            }
        }
        if (flag) {
            nexus.world.setWorldTime(6000);
        }
    }

    @Override
    public void onRitual(EntityDimensionalNexus nexus) {
        if (nexus.getRiteTicks() % 2 == 0) {
            Elementaristics.proxy.generateGenericParticles(nexus.world, nexus.posX, nexus.posY + 0.5F, nexus.posZ, Aspects.light.getColor(), 3, 100, -0.1F, false, false);
            Elementaristics.proxy.generateGenericParticles(nexus.world, nexus.posX, nexus.posY + 0.5F, nexus.posZ, 14466048, 2, 80, -0.05F, false, false);
        }
    }
}
