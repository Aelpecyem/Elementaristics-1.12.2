package de.aelpecyem.elementaristics.misc.pantheon.advanced;

import com.google.common.base.Predicate;
import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.blocks.tileentity.pantheon.TileEntityDeityShrine;
import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import de.aelpecyem.elementaristics.misc.pantheon.DeityBase;
import de.aelpecyem.elementaristics.util.TimeUtil;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class DeityQueen extends DeityBase {

    public DeityQueen() {
        super(TimeUtil.getTickTimeStartForHour(14), new ResourceLocation(Elementaristics.MODID, "deity_queen"), 6302785);
    }

    @Override
    public void setUpTile(TileEntityDeityShrine te) {
        te.storage.setMaxReceive(200);
        te.storage.setMaxExtract(200);
        super.setUpTile(te);
    }

    @Override
    public void symbolEffect(TileEntityDeityShrine te) {
        super.symbolEffect(te);
        List<EntityPlayer> targets = te.getWorld().getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(te.getPos().getX() - 1, te.getPos().getY() - 1, te.getPos().getZ() - 1, te.getPos().getX() + 2, te.getPos().getY() + 2, te.getPos().getZ() + 2), new Predicate<EntityPlayer>() {
            @Override
            public boolean apply(@Nullable EntityPlayer input) {
                if(input.getFoodStats().getFoodLevel() > 6)
                    return true;
                return false;
            }
        });

        if (!targets.isEmpty()) {
            for (EntityPlayer player : targets) {
                if (te.storage.getEnergyStored() < te.storage.getMaxEnergyStored()) {
                    player.getFoodStats().addExhaustion(1); //play with the number
                    player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 100, 0, false, true));
                    te.storage.receiveEnergy(5, false);
                }
                       //also add hunger
            }
        }
    }
}


