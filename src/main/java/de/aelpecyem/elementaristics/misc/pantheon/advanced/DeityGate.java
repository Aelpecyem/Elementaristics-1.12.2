package de.aelpecyem.elementaristics.misc.pantheon.advanced;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.blocks.tileentity.tile.pantheon.TileEntityDeityShrine;
import de.aelpecyem.elementaristics.events.LivingEventHandler;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.pantheon.DeitySupplyEffectBase;
import de.aelpecyem.elementaristics.util.TimeUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;

public class DeityGate extends DeitySupplyEffectBase {
    public DeityGate() {
        super(TimeUtil.getTickTimeStartForHour(7), new ResourceLocation(Elementaristics.MODID, "gate_and_key"), Aspects.mind, 1131335);
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
        List<EntityLivingBase> victims = te.getWorld().getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(te.getPos().getX() - 16, te.getPos().getY() - 8, te.getPos().getZ() - 16, te.getPos().getX() + 16, te.getPos().getY() + 8, te.getPos().getZ() + 16));
        if (!victims.isEmpty()) {
            for (EntityLivingBase victim : victims) {
                if (!victim.isEntityUndead() && victim.hurtTime == 2) {
                    if (victim.getEntityData().hasKey(LivingEventHandler.LAST_DMG_STRING)) {
                        te.storage.receiveEnergy(victim.getEntityData().getInteger(LivingEventHandler.LAST_DMG_STRING) * 10, false);
                    }
                }
            }
        }
    }
}


