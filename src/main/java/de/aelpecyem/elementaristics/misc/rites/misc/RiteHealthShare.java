package de.aelpecyem.elementaristics.misc.rites.misc;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.entity.nexus.EntityDimensionalNexus;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.potions.PotionInit;
import de.aelpecyem.elementaristics.misc.rites.RiteBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class RiteHealthShare extends RiteBase {

    public RiteHealthShare() {
        super(new ResourceLocation(Elementaristics.MODID, "rite_health_share"), 200, 0.4F, 6, Aspects.earth, Aspects.life);
    }

    @Override
    public void doMagic(EntityDimensionalNexus nexus) {
        List<EntityPlayer> targets = nexus.world.getEntitiesWithinAABB(EntityPlayer.class, nexus.getEntityBoundingBox().grow(10));
        for (EntityPlayer playerAffected : targets) {
            playerAffected.addPotionEffect(new PotionEffect(PotionInit.potionHealthShare, 8000, 0, true, true));
        }
    }

    @Override
    public void onRitual(EntityDimensionalNexus nexus) {
        if (nexus.getRiteTicks() % 2 == 0) {
            Elementaristics.proxy.generateGenericParticles(nexus.world, nexus.posX, nexus.posY + 0.5F, nexus.posZ, nexus.world.rand.nextGaussian() * 0.02D,
                    nexus.world.rand.nextFloat() * -0.03D,
                    nexus.world.rand.nextGaussian() * 0.02D, 12863556, 1 + nexus.world.rand.nextFloat(), 100, 0, false, true);
            for (EntityPlayer player : nexus.getPlayersInArea(false)) {
                Elementaristics.proxy.generateGenericParticles(player, Aspects.life.getColor(), 1, 100, 0.1F, true, true);
            }
        }
    }
}
