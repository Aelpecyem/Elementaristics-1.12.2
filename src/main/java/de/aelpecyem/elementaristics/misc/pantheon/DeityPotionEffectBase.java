package de.aelpecyem.elementaristics.misc.pantheon;

import com.google.common.base.Predicate;
import de.aelpecyem.elementaristics.blocks.tileentity.pantheon.TileEntityDeityShrine;
import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;

import javax.annotation.Nullable;
import java.util.List;

public class DeityPotionEffectBase extends DeityBase {
    Potion effect;
    boolean playersOnly;

    public DeityPotionEffectBase(long tickTimeBegin, ResourceLocation name, int color, Potion effect) {
        super(tickTimeBegin, name, color);
        this.effect = effect;
        this.playersOnly = true;
    }

    public DeityPotionEffectBase(long tickTimeBegin, ResourceLocation name, int color, Potion effect, boolean playersOnly) {
        super(tickTimeBegin, name, color);
        this.effect = effect;
        this.playersOnly = playersOnly;
    }

    public void addEffect(EntityLivingBase target, boolean isStatue) {
        target.addPotionEffect(new PotionEffect(effect, isStatue ? 1200 : 400, isStatue ? 1 : 0, false, false));
        if (target.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
            IPlayerCapabilities cap = target.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
            if (cap.getTimeStunted() < 1) {
                cap.fillMagan(isStatue ? 0.1F : 0.05F);
            }
        }
    }

    @Override
    public void symbolEffect(TileEntityDeityShrine te) {
        if (te.storage.extractIfPossible(8)) {
            List<EntityLivingBase> targets = te.getWorld().getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(te.getPos().getX() - 24, te.getPos().getY() - 12, te.getPos().getZ() - 24, te.getPos().getX() + 24, te.getPos().getY() + 12, te.getPos().getZ() + 24), new Predicate<EntityLivingBase>() {
                @Override
                public boolean apply(@Nullable EntityLivingBase input) {
                    if (playersOnly && input instanceof EntityPlayer)
                        return true;
                    else {
                        return !playersOnly;
                    }
                }
            });

            if (!targets.isEmpty()) {
                for (EntityLivingBase target : targets) {
                    addEffect(target, false);
                }
            }
            super.symbolEffect(te);
        }
    }

    @Override
    public void statueEffect(TileEntityDeityShrine te) {
        if (te.storage.extractIfPossible(4)) {
            List<EntityLivingBase> targets = te.getWorld().getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(te.getPos().getX() - 32, te.getPos().getY() - 24, te.getPos().getZ() - 32, te.getPos().getX() + 32, te.getPos().getY() + 24, te.getPos().getZ() + 32), new Predicate<EntityLivingBase>() {
                @Override
                public boolean apply(@Nullable EntityLivingBase input) {
                    if (playersOnly && input instanceof EntityPlayer)
                        return true;
                    else {
                        return !playersOnly;
                    }
                }
            });

            if (!targets.isEmpty()) {
                for (EntityLivingBase target : targets) {
                    addEffect(target, false);
                }
            }
            super.symbolEffect(te);
        }
        super.statueEffect(te);
    }

    @Override
    public void sacrificeEffect(TileEntityDeityShrine te) {

    }

    @Override
    public void activeStatueEffect(TileEntityDeityShrine te) {

    }
}
