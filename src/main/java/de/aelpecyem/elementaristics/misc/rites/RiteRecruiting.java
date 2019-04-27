package de.aelpecyem.elementaristics.misc.rites;

import com.google.common.base.Predicate;
import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import de.aelpecyem.elementaristics.entity.EntityCultist;
import de.aelpecyem.elementaristics.items.base.artifacts.rites.IHasRiteUse;
import de.aelpecyem.elementaristics.items.base.artifacts.rites.IncantationBase;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class RiteRecruiting extends RiteBase {

    public RiteRecruiting() {
        super(new ResourceLocation(Elementaristics.MODID, "rite_recruiting"), 200, 0.4F, 8, Aspects.soul);
    }

    @Override
    public void doMagic(World world, BlockPos pos, EntityPlayer player) {
        if (player != null) {
            List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos.getX() - 2, pos.getY() - 1, pos.getZ() - 2, pos.getX() + 2, pos.getY() + 2, pos.getZ() + 2), null);
            List<EntityVillager> villagers = world.getEntitiesWithinAABB(EntityVillager.class, new AxisAlignedBB(pos.getX() - 2, pos.getY() - 1, pos.getZ() - 2, pos.getX() + 2, pos.getY() + 2, pos.getZ() + 2), null);
            if (!world.isRemote) {
                if (!items.isEmpty() && !villagers.isEmpty()) {
                    for (EntityItem item : items) {
                        if (item.getItem().getItem() instanceof IHasRiteUse && !((IHasRiteUse) item.getItem().getItem()).isConsumed() && !(item.getItem().getItem() instanceof IncantationBase)) {
                            villagers.get(0).setDead();
                            EntityCultist cultist = new EntityCultist(world);
                            cultist.setPosition(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
                            cultist.setAspect(((IHasRiteUse) item.getItem().getItem()).getAspects().get(0));
                            cultist.setOwnerId(player.getUniqueID());
                            doParticleBurst(world, pos, ((IHasRiteUse) item.getItem().getItem()).getAspects().get(0).getColor());
                            item.getItem().shrink(1);
                            world.spawnEntity(cultist);
                            break;

                        }
                    }
                }
            }
            if (player.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
                IPlayerCapabilities cap = player.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
                cap.setCultistCount(world.getEntities(EntityCultist.class, new Predicate<EntityCultist>() {
                    @Override
                    public boolean apply(@Nullable EntityCultist input) {
                        return input.isOwner(player);
                    }
                }).size());
            }
        }

    }

    @Override
    public void onRitual(World world, BlockPos altarPos, List<EntityPlayer> players, int tickCount) {
        Elementaristics.proxy.generateGenericParticles(world, altarPos.getX() + 0.5F, altarPos.getY() + 1F, altarPos.getZ() + 0.5F, Aspects.soul.getColor(), 3, 60, 0, false, false);
        List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(altarPos.getX() - 6, altarPos.getY() - 2, altarPos.getZ() - 6, altarPos.getX() + 6, altarPos.getY() + 3, altarPos.getZ() + 6), null);
        List<EntityVillager> villagers = world.getEntitiesWithinAABB(EntityVillager.class, new AxisAlignedBB(altarPos.getX() - 6, altarPos.getY() - 2, altarPos.getZ() - 6, altarPos.getX() + 6, altarPos.getY() + 3, altarPos.getZ() + 6), null);

        for (EntityVillager villager : villagers) {
            villager.motionX = (altarPos.getX() + 0.5 - villager.posX) / 20;
            villager.motionY = (altarPos.getY() + 1.5 - villager.posY) / 20;
            villager.motionZ = (altarPos.getZ() + 0.5 - villager.posZ) / 20;
            Elementaristics.proxy.generateGenericParticles(villager, Aspects.soul.getColor(), 1, 10, 0, false, false);

        }
        for (EntityItem item : items) {
            item.motionX = (altarPos.getX() + 0.5 - item.posX) / 20;
            item.motionY = (altarPos.getY() + 2 - item.posY) / 20;
            item.motionZ = (altarPos.getZ() + 0.5 - item.posZ) / 20;
            Elementaristics.proxy.generateGenericParticles(item, Aspects.soul.getColor(), 1, 10, 0, false, false);
            Elementaristics.proxy.generateGenericParticles(world, altarPos.getX() + 0.5, altarPos.getY() + 2, altarPos.getZ() + 0.5, item.getItem().getItem() instanceof IHasRiteUse ? ((IHasRiteUse) item.getItem().getItem()).getAspects().get(0).getColor() : Aspects.soul.getColor(), 4, 200, 0, false, true);
        }
    }

    public void doParticleBurst(World world, BlockPos pos, int color) {
        for (int i = 0; i < 100; i++) {
            double motionX = world.rand.nextGaussian() * 0.1D;
            double motionY = world.rand.nextGaussian() * 0.1D;
            double motionZ = world.rand.nextGaussian() * 0.1D;
            Elementaristics.proxy.generateGenericParticles(world, pos.getX() + 0.5, pos.getY() + 2, pos.getZ() + 0.5, motionX, motionY, motionZ, color, 4, 100, 0, true, true);
        }
    }
}
