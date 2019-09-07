package de.aelpecyem.elementaristics.misc.pantheon;

import de.aelpecyem.elementaristics.blocks.tileentity.pantheon.TileEntityDeityShrine;
import de.aelpecyem.elementaristics.misc.elements.Aspect;
import net.minecraft.client.model.ModelBase;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class DeitySupplyEffectBase extends Deity {
    public DeitySupplyEffectBase(long tickTimeBegin, ResourceLocation name, @Nullable Aspect aspect, int color) {
        super(tickTimeBegin, name, aspect, color);
    }

    public DeitySupplyEffectBase(long tickTimeBegin, ResourceLocation name, @Nullable Aspect aspect, int color, ModelBase model, ResourceLocation texture) {
        super(tickTimeBegin, name, aspect, color, model, texture);
    }

    public void supplyEffect(TileEntityDeityShrine te, boolean isStatue) {
        if (te.getNexusBound() != null) {
            te.getNexusBound().addAspect(getAspect());
        }
    }

    @Override
    public void symbolEffect(TileEntityDeityShrine te) {
        if (!te.isPassiveEffectManipulatorBelow() && te.getNexusBound() != null && te.storage.extractIfPossible(5)) {
            supplyEffect(te, false);
        }
        passiveParticles(te);
    }

    @Override
    public void statueEffect(TileEntityDeityShrine te) {
        if (!te.isPassiveEffectManipulatorBelow() && te.getNexusBound() != null && te.storage.extractIfPossible(2)) {
            supplyEffect(te, true);
        }
        passiveParticles(te);
    }


    @Override
    public void sacrificeEffect(TileEntityDeityShrine te) {

    }

    @Override
    public void activeStatueEffect(TileEntityDeityShrine te) {

    }
}
