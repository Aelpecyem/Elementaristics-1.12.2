package de.aelpecyem.elementaristics.misc.rites.misc;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.entity.nexus.EntityDimensionalNexus;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.potions.PotionInit;
import de.aelpecyem.elementaristics.misc.rites.RiteBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class RiteEmptiness extends RiteBase {

    public RiteEmptiness() {
        super(new ResourceLocation(Elementaristics.MODID, "rite_emptiness"), 400, 0.2F, 6, Aspects.vacuum);
    }

    @Override
    public void doMagic(EntityDimensionalNexus nexus) {
        List<EntityPlayer> targets = nexus.world.getEntitiesWithinAABB(EntityPlayer.class, nexus.getEntityBoundingBox().grow(10));
        for (EntityPlayer playerAffected : targets) {
            playerAffected.removePotionEffect(PotionInit.laughter);
            playerAffected.removePotionEffect(PotionInit.silence);
            playerAffected.removePotionEffect(PotionInit.dread);
            playerAffected.removePotionEffect(PotionInit.contentment);
            playerAffected.removePotionEffect(PotionInit.ecstasy);
            playerAffected.removePotionEffect(PotionInit.fear);
        }
    }

    @Override
    public void onRitual(EntityDimensionalNexus nexus) {
        if (nexus.getRiteTicks() % 2 == 0) {
            Elementaristics.proxy.generateGenericParticles(nexus.world, nexus.posX, nexus.posY, nexus.posZ, Aspects.vacuum.getColor(), 3, 60, 0, false, false);
            for (EntityPlayer player : nexus.getPlayersInArea(false)) {
                Elementaristics.proxy.generateGenericParticles(player, Aspects.vacuum.getColor(), 0.5F, 100, 0, false, true);
            }
        }
    }
}
