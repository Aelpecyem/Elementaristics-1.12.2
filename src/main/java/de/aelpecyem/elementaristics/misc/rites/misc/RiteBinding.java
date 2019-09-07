package de.aelpecyem.elementaristics.misc.rites.misc;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.entity.nexus.EntityDimensionalNexus;
import de.aelpecyem.elementaristics.items.base.artifacts.ItemHeartStone;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.potions.PotionInit;
import de.aelpecyem.elementaristics.misc.rites.RiteBase;
import de.aelpecyem.elementaristics.util.MiscUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class RiteBinding extends RiteBase {

    public RiteBinding() {
        super(new ResourceLocation(Elementaristics.MODID, "rite_binding"), 150, 1.5F, 12, Aspects.body, Aspects.earth);
    }

    @Override
    public void doMagic(EntityDimensionalNexus nexus) {
        EntityPlayer player = nexus.world.getClosestPlayerToEntity(nexus, 20);
        if (player != null) {
            List<EntityItem> items = nexus.world.getEntitiesWithinAABB(EntityItem.class, nexus.getEntityBoundingBox().grow(2), input -> input.getItem().getItem() instanceof ItemHeartStone);
            List<EntityLivingBase> targets = nexus.world.getEntitiesWithinAABB(EntityLivingBase.class, nexus.getEntityBoundingBox().grow(4), (EntityLivingBase b) -> b.getActivePotionEffects().contains(b.getActivePotionEffect(PotionInit.potionPotential)));
            boolean flag = !items.isEmpty();

            if (flag) {
                if (!targets.isEmpty()) {
                    for (EntityLivingBase base : targets) {
                        MiscUtil.addEntityToBoundEntities(player, base);
                        base.removePotionEffect(PotionInit.potionPotential);
                        return;
                    }
                }
            }
        }

    }

    @Override
    public void onRitual(EntityDimensionalNexus nexus) {
        Elementaristics.proxy.generateGenericParticles(nexus.world, nexus.posX, nexus.posY + 0.5F, nexus.posZ, Aspects.earth.getColor(), 3, 60, 0, false, false);
        EntityPlayer player = nexus.world.getClosestPlayerToEntity(nexus, 20);

        List<EntityItem> items = nexus.world.getEntitiesWithinAABB(EntityItem.class, nexus.getEntityBoundingBox().grow(2), input -> input.getItem().getItem() instanceof ItemHeartStone);
        List<EntityLivingBase> targets = nexus.world.getEntitiesWithinAABB(EntityLivingBase.class, nexus.getEntityBoundingBox().grow(4), (EntityLivingBase b) -> b.getActivePotionEffects().contains(b.getActivePotionEffect(PotionInit.potionPotential)));

        for (EntityItem item : items) {
            if (nexus.getRiteTicks() % 20 == 0 && player != null)
                Elementaristics.proxy.generateGenericParticles(player, Aspects.mana.getColor(), 2, 100, 0, false, true);
            nexus.suckInEntity(item, item.height / 2);
        }

        for (EntityLivingBase living : targets) {
            Elementaristics.proxy.generateGenericParticles(living, Aspects.earth.getColor(), 3, 100, 0, false, true);
        }
    }
}
