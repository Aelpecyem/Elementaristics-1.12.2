package de.aelpecyem.elementaristics.misc.pantheon;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityAltar;
import de.aelpecyem.elementaristics.blocks.tileentity.pantheon.TileEntityDeityShrine;
import de.aelpecyem.elementaristics.misc.elements.Aspect;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class DeityBase extends Deity {
    //as of now, this does nothing
    public DeityBase(long tickTimeBegin, ResourceLocation name, int color) {
        super(tickTimeBegin, name, null, color);
    }


    @Override
    public void symbolEffect(TileEntityDeityShrine te) {

    }

    @Override
    public void statueEffect(TileEntityDeityShrine te) {

    }


    @Override
    public void sacrificeEffect(TileEntityDeityShrine te) {

    }

    @Override
    public void activeStatueEffect(TileEntityDeityShrine te) {

    }
}
