package de.aelpecyem.elementaristics.misc.pantheon.advanced;

import com.google.common.base.Predicate;
import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityAltar;
import de.aelpecyem.elementaristics.blocks.tileentity.pantheon.TileEntityDeityShrine;
import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import de.aelpecyem.elementaristics.capability.player.souls.Soul;
import de.aelpecyem.elementaristics.init.SoulInit;
import de.aelpecyem.elementaristics.misc.elements.Aspect;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.pantheon.DeitySupplyEffectBase;
import de.aelpecyem.elementaristics.util.TimeUtil;
import javafx.print.PageLayout;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;

import javax.annotation.Nullable;
import java.util.List;

public class DeityDragon extends DeitySupplyEffectBase {
    Soul soul;

    public DeityDragon(int hour, Aspect aspect, Soul soul, int color) {
        super(TimeUtil.getTickTimeStartForHour(hour), new ResourceLocation(Elementaristics.MODID, "dragon_" + aspect.getName()), aspect, color);
        this.soul = soul;
    }
    //Todo


    @Override
    public void symbolEffect(TileEntityDeityShrine te) {
        super.symbolEffect(te);
        if (te.storage.extractIfPossible(5)) {

            List<EntityPlayer> players = te.getWorld().getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(te.getPos().getX() - 16, te.getPos().getY() - 8, te.getPos().getZ() - 16, te.getPos().getX() + 16, te.getPos().getY() + 8, te.getPos().getZ() + 16), new Predicate<EntityPlayer>() {
                @Override
                public boolean apply(@Nullable EntityPlayer input) {
                    if (input.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
                        IPlayerCapabilities cap = input.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
                        if (cap.getSoul() == soul || cap.getSoul() == SoulInit.soulDragon) {
                            return true;
                        }
                    }
                    return false;
                }
            });
            if (!players.isEmpty()) {
                for (EntityPlayer player : players) {
                    IPlayerCapabilities cap = player.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
                    if (cap.getTimeStunted() < 1) {
                        cap.fillMagan(0.4F);
                        player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 200, 0, false, false));
                        player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 200, 0, false, false));
                        player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 200, 0, false, false));

                    }
                }
            }
        }
    }
}

