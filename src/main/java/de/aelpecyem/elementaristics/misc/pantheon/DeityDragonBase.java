package de.aelpecyem.elementaristics.misc.pantheon;

import de.aelpecyem.elementaristics.blocks.tileentity.pantheon.TileEntityDeityShrine;
import de.aelpecyem.elementaristics.capability.player.souls.Soul;
import de.aelpecyem.elementaristics.misc.elements.Aspect;
import net.minecraft.util.ResourceLocation;

public class DeityDragonBase extends Deity {
    protected Soul soulBuffing;

    public DeityDragonBase(int tickTimeStart, int tickTimeEnd, ResourceLocation name, Aspect aspect, Soul soulBuffing) {
        super(tickTimeStart, tickTimeEnd, name, aspect);
        this.soulBuffing = soulBuffing;
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
