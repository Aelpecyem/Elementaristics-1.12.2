package de.aelpecyem.elementaristics.misc.rites.misc;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.entity.nexus.EntityDimensionalNexus;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.rites.RiteBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class RiteFeast extends RiteBase {
    //doot doot, the game's first rite
    public RiteFeast() {
        super(new ResourceLocation(Elementaristics.MODID, "rite_feast"), 400, 0.2F, 6, Aspects.body, Aspects.life);
    }

    @Override
    public void doMagic(EntityDimensionalNexus nexus) {
        List<EntityPlayer> targets = nexus.world.getEntitiesWithinAABB(EntityPlayer.class, nexus.getEntityBoundingBox().grow(10));
        for (EntityPlayer playerAffected : targets) {
            playerAffected.getFoodStats().setFoodLevel(playerAffected.getFoodStats().getFoodLevel() + 5);
            playerAffected.getFoodStats().setFoodSaturationLevel(playerAffected.getFoodStats().getSaturationLevel() + 5);
            playerAffected.addPotionEffect(new PotionEffect(Potion.getPotionById(23), 8000, 1, false, false));
        }
    }

    @Override
    public void onRitual(EntityDimensionalNexus nexus) {
        if (nexus.getRiteTicks() % 2 == 0) {
            Elementaristics.proxy.generateGenericParticles(nexus.world, nexus.posX, nexus.posY, nexus.posZ, 12863556, 3, 60, 0, false, false);
            for (EntityPlayer player : nexus.getPlayersInArea(false)) {
                Elementaristics.proxy.generateGenericParticles(player, 12863556, 1, 100, 0, false, true);
            }
        }
    }
}
