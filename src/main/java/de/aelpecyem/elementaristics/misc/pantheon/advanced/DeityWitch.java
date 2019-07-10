package de.aelpecyem.elementaristics.misc.pantheon.advanced;

import com.google.common.base.Predicate;
import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.blocks.tileentity.pantheon.TileEntityDeityShrine;
import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import de.aelpecyem.elementaristics.misc.elements.Aspect;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.pantheon.DeitySupplyEffectBase;
import de.aelpecyem.elementaristics.util.MaganUtil;
import de.aelpecyem.elementaristics.util.TimeUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;

import javax.annotation.Nullable;
import java.util.List;

public class DeityWitch extends DeitySupplyEffectBase {
    public DeityWitch() {
        super(TimeUtil.getTickTimeStartForHour(23), new ResourceLocation(Elementaristics.MODID, "deity_witch"), Aspects.magan, 15887104);
    }


    @Override
    public void symbolEffect(TileEntityDeityShrine te) {
        if (te.storage.extractIfPossible(5)) {
            List<EntityPlayer> targets = te.getWorld().getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(te.getPos().getX() - 24, te.getPos().getY() - 12, te.getPos().getZ() - 24, te.getPos().getX() + 24, te.getPos().getY() + 12, te.getPos().getZ() + 24), new Predicate<EntityPlayer>() {
                @Override
                public boolean apply(@Nullable EntityPlayer input) {
                    if(input.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null))
                        return true;
                    return false;
                }
            });

            if (!targets.isEmpty()) {
                for (EntityPlayer player : targets) {
                    if (player.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
                        IPlayerCapabilities cap = player.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
                        if (cap.getTimeStunted() < 1) {
                            cap.fillMagan(0.5F);
                        }else{
                            cap.stuntMagan(cap.getTimeStunted() - 1);
                        }
                    }
                }
            }
            super.symbolEffect(te);
        }
    }
}
