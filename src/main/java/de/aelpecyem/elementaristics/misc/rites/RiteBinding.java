package de.aelpecyem.elementaristics.misc.rites;

import com.google.common.base.Predicate;
import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import de.aelpecyem.elementaristics.entity.EntityCultist;
import de.aelpecyem.elementaristics.items.base.artifacts.ItemHeartStone;
import de.aelpecyem.elementaristics.items.base.artifacts.rites.IHasRiteUse;
import de.aelpecyem.elementaristics.items.base.artifacts.rites.IncantationBase;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.potions.PotionInit;
import de.aelpecyem.elementaristics.util.MiscUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class RiteBinding extends RiteBase {

    public RiteBinding() {
        super(new ResourceLocation(Elementaristics.MODID, "rite_binding"), 150, 1.5F, 12, Aspects.soul, Aspects.earth);
    }

    @Override
    public void doMagic(World world, BlockPos pos, EntityPlayer player) {
        if (player != null) {
            List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos.getX() - 2, pos.getY() - 1, pos.getZ() - 2, pos.getX() + 2, pos.getY() + 2, pos.getZ() + 2), null);
            List<EntityLivingBase> targets = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(pos.getX() - 4, pos.getY() - 3, pos.getZ() - 4, pos.getX() + 4, pos.getY() + 4, pos.getZ() + 4), null);
            boolean flag = false;
            for (EntityItem item : items) {
                if (item.getItem().getItem() instanceof ItemHeartStone) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                if (!targets.isEmpty()) {
                    for (EntityLivingBase base : targets) {
                        if (base.getActivePotionEffects().contains(base.getActivePotionEffect(PotionInit.potionPotential))) {
                            MiscUtil.addEntityToBoundEntities(player, base);
                            base.removePotionEffect(PotionInit.potionPotential);
                            return;
                        }

                    }
                }
            }
        }

    }

    @Override
    public void onRitual(World world, BlockPos altarPos, List<EntityPlayer> players, int tickCount) {
        Elementaristics.proxy.generateGenericParticles(world, altarPos.getX() + 0.5F, altarPos.getY() + 1F, altarPos.getZ() + 0.5F, Aspects.earth.getColor(), 3, 60, 0, false, false);
        EntityPlayer player = world.getClosestPlayer(altarPos.getX(), altarPos.getY(), altarPos.getZ(), 20, false);
        Elementaristics.proxy.generateGenericParticles(player, Aspects.mana.getColor(), 3, 100, 0, false, true);

        List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(altarPos.getX() - 2, altarPos.getY() - 1, altarPos.getZ() - 2, altarPos.getX() + 2, altarPos.getY() + 2, altarPos.getZ() + 2), null);
        List<EntityLivingBase> targets = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(altarPos.getX() - 4, altarPos.getY() - 3, altarPos.getZ() - 4, altarPos.getX() + 4, altarPos.getY() + 4, altarPos.getZ() + 4), null);

        for (EntityItem item : items) {
            if (item.getItem().getItem() instanceof ItemHeartStone) {
                Elementaristics.proxy.generateGenericParticles(player, Aspects.mana.getColor(), 3, 100, 0, false, true);
                item.motionX = (altarPos.getX() + 0.5 - item.posX) / 20;
                item.motionY = (altarPos.getY() + 1.5 - item.posY) / 20;
                item.motionZ = (altarPos.getZ() + 0.5 - item.posZ) / 20;

            }
        }

        for (EntityLivingBase living : targets) {
            if (living != player && living.getActivePotionEffects().contains(living.getActivePotionEffect(PotionInit.potionPotential))) {
                Elementaristics.proxy.generateGenericParticles(player, Aspects.earth.getColor(), 3, 100, 0, false, true);
            }
        }
    }
}
