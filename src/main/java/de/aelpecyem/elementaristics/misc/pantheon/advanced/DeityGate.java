package de.aelpecyem.elementaristics.misc.pantheon.advanced;

import com.google.common.base.Predicate;
import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.blocks.tileentity.pantheon.TileEntityDeityShrine;
import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import de.aelpecyem.elementaristics.capability.player.souls.Soul;
import de.aelpecyem.elementaristics.events.EventHandler;
import de.aelpecyem.elementaristics.init.SoulInit;
import de.aelpecyem.elementaristics.misc.elements.Aspect;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.pantheon.DeitySupplyEffectBase;
import de.aelpecyem.elementaristics.util.TimeUtil;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.IFMLCallHook;

import javax.annotation.Nullable;
import java.util.List;

public class DeityGate extends DeitySupplyEffectBase {
    public DeityGate() {
        super(TimeUtil.getTickTimeStartForHour(7), new ResourceLocation(Elementaristics.MODID, "gate_and_key"), Aspects.mind, 1131335);
    }
    //Todo


    @Override
    public void symbolEffect(TileEntityDeityShrine te) {
        super.symbolEffect(te);
        List<EntityLivingBase> victims = te.getWorld().getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(te.getPos().getX() - 16, te.getPos().getY() - 8, te.getPos().getZ() - 16, te.getPos().getX() + 16, te.getPos().getY() + 8, te.getPos().getZ() + 16));
        if (!victims.isEmpty()) {
            for (EntityLivingBase victim : victims) {
                if (victim.hurtTime == 5) {
                    if (victim.getEntityData().hasKey(EventHandler.LAST_DMG_STRING)) {
                        te.storage.receiveEnergy(Math.round(victim.getEntityData().getFloat(EventHandler.LAST_DMG_STRING) * 10), false);
                    }
                }
            }
        }
    }
}


