package de.aelpecyem.elementaristics.misc.pantheon;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityAltar;
import de.aelpecyem.elementaristics.blocks.tileentity.pantheon.TileEntityDeityShrine;
import de.aelpecyem.elementaristics.items.base.artifacts.rites.IHasRiteUse;
import de.aelpecyem.elementaristics.misc.elements.Aspect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class DeitySupplyEffectBase extends Deity {
    public DeitySupplyEffectBase(long tickTimeBegin, ResourceLocation name, @Nullable Aspect aspect, int color) {
        super(tickTimeBegin, name, aspect, color);
    }

    public void supplyEffect(TileEntityDeityShrine te) {
        if (te.getAltarBound() != null) {
            TileEntityAltar tile = te.getAltarBound();
            tile.addAspect(getAspect());
            te.doParticles(this);
        }
    }

    @Override
    public void symbolEffect(TileEntityDeityShrine te) {
        if (te.storage.extractIfPossible(5)) {
            supplyEffect(te);
        }
    }

    @Override
    public void statueEffect(TileEntityDeityShrine te) {
        if (te.storage.extractIfPossible(2)) {
            supplyEffect(te);
        }
    }


    @Override
    public void sacrificeEffect(TileEntityDeityShrine te) {

    }

    @Override
    public void activeStatueEffect(TileEntityDeityShrine te) {

    }
}
