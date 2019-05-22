package de.aelpecyem.elementaristics.misc.pantheon;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityAltar;
import de.aelpecyem.elementaristics.blocks.tileentity.pantheon.TileEntityDeityShrine;
import de.aelpecyem.elementaristics.misc.elements.Aspect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class DeitySupplyEffectBase extends Deity {
    public DeitySupplyEffectBase(long tickTimeBegin, ResourceLocation name, @Nullable Aspect aspect, int color) {
        super(tickTimeBegin, name, aspect, color);
    }

    public void grantAspect(TileEntityDeityShrine te) {
        if (te.altarPos != null) {
            if (te.getWorld().getTileEntity(te.altarPos) instanceof TileEntityAltar) {
                TileEntityAltar altar = (TileEntityAltar) te.getWorld().getTileEntity(te.altarPos);
                if (getAspect() != null)
                    altar.addAspectsExternally(getAspect());
            }
        }
    }

    @Override
    public void symbolEffect(TileEntityDeityShrine te) {
        if (te.storage.extractIfPossible(5)) {
            grantAspect(te);
        }
    }

    @Override
    public void statueEffect(TileEntityDeityShrine te) {
        if (te.storage.extractIfPossible(2)) {
            Elementaristics.proxy.generateGenericParticles(te.getWorld(), te.getPos().getX() + te.getWorld().rand.nextFloat(), te.getPos().getY() + te.getWorld().rand.nextFloat(), te.getPos().getZ() + te.getWorld().rand.nextFloat(), getColor(), 2, 50, 0, false, false);
            grantAspect(te);
        }
    }

    @Override
    public void sacrificeEffect(TileEntityDeityShrine te) {

    }

    @Override
    public void activeStatueEffect(TileEntityDeityShrine te) {

    }
}
