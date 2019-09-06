package de.aelpecyem.elementaristics.misc.rites.crafting;

import com.google.common.base.Predicate;
import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import de.aelpecyem.elementaristics.entity.EntityCultist;
import de.aelpecyem.elementaristics.entity.nexus.EntityDimensionalNexus;
import de.aelpecyem.elementaristics.items.base.artifacts.rites.IHasRiteUse;
import de.aelpecyem.elementaristics.items.base.artifacts.rites.IncantationBase;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.rites.RiteBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.List;

public class RiteRecruiting extends RiteBase {

    public RiteRecruiting() {
        super(new ResourceLocation(Elementaristics.MODID, "rite_recruiting"), 200, 0.4F, 8, Aspects.soul);
    }

    @Override
    public void doMagic(EntityDimensionalNexus nexus) {
        EntityPlayer player = nexus.world.getClosestPlayerToEntity(nexus, 20);
        if (player != null) {
            List<EntityItem> items = nexus.world.getEntitiesWithinAABB(EntityItem.class, nexus.getEntityBoundingBox().grow(2));
            List<EntityVillager> villagers = nexus.world.getEntitiesWithinAABB(EntityVillager.class, nexus.getEntityBoundingBox().grow(2));
            if (!nexus.world.isRemote) {
                if (!items.isEmpty() && !villagers.isEmpty()) {
                    for (EntityItem item : items) {
                        if (item.getItem().getItem() instanceof IHasRiteUse && !((IHasRiteUse) item.getItem().getItem()).isConsumed() && !(item.getItem().getItem() instanceof IncantationBase)) {
                            EntityCultist cultist = new EntityCultist(nexus.world);
                            cultist.setPosition(nexus.posX, nexus.posY, nexus.posZ);
                            cultist.setAspect(((IHasRiteUse) item.getItem().getItem()).getAspects().get(0));
                            cultist.setTamedBy(player);
                            cultist.setCustomNameTag(villagers.get(0).getCustomNameTag());
                            villagers.get(0).setDead();
                            doParticleBurst(nexus, ((IHasRiteUse) item.getItem().getItem()).getAspects().get(0).getColor());
                            item.getItem().shrink(1);
                            nexus.world.spawnEntity(cultist);
                            break;

                        }
                    }
                }
            }
            if (player.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
                IPlayerCapabilities cap = player.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
                cap.setCultistCount(nexus.world.getEntities(EntityCultist.class, new Predicate<EntityCultist>() {
                    @Override
                    public boolean apply(@Nullable EntityCultist input) {
                        return input.isOwner(player);
                    }
                }).size());
            }
        }

    }

    @Override
    public void onRitual(EntityDimensionalNexus nexus) {
        Elementaristics.proxy.generateGenericParticles(nexus.world, nexus.posX, nexus.posY, nexus.posZ, Aspects.soul.getColor(), 3, 60, 0, false, false);
        List<EntityItem> items = nexus.world.getEntitiesWithinAABB(EntityItem.class, nexus.getEntityBoundingBox().grow(6));
        List<EntityVillager> villagers = nexus.world.getEntitiesWithinAABB(EntityVillager.class, nexus.getEntityBoundingBox().grow(6));

        if (!villagers.isEmpty()) {
            villagers.get(0).motionX = (nexus.posX - villagers.get(0).posX) / 20;
            villagers.get(0).motionY = (nexus.posY - villagers.get(0).posY) / 20;
            villagers.get(0).motionZ = (nexus.posZ - villagers.get(0).posZ) / 20;
            Elementaristics.proxy.generateGenericParticles(villagers.get(0), Aspects.soul.getColor(), 1, 10, 0, false, false);

        }
        for (EntityItem item : items) {
            item.motionX = (nexus.posX - item.posX) / 20;
            item.motionY = (nexus.posY - item.posY) / 20;
            item.motionZ = (nexus.posZ - item.posZ) / 20;
            Elementaristics.proxy.generateGenericParticles(item, Aspects.soul.getColor(), 1, 10, 0, false, false);
            Elementaristics.proxy.generateGenericParticles(nexus.world, nexus.posX, nexus.posY, nexus.posZ, item.getItem().getItem() instanceof IHasRiteUse ? ((IHasRiteUse) item.getItem().getItem()).getAspects().get(0).getColor() : Aspects.soul.getColor(), 4, 200, 0, false, true);
        }
    }

    public void doParticleBurst(EntityDimensionalNexus nexus, int color) {
        for (int i = 0; i < 100; i++) {
            double motionX = nexus.world.rand.nextGaussian() * 0.1D;
            double motionY = nexus.world.rand.nextGaussian() * 0.1D;
            double motionZ = nexus.world.rand.nextGaussian() * 0.1D;
            Elementaristics.proxy.generateGenericParticles(nexus.world, nexus.posX, nexus.posY, nexus.posZ, motionX, motionY, motionZ, color, 4, 100, 0, true, true);
        }
    }
}
