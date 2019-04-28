package de.aelpecyem.elementaristics.misc.pantheon;

import de.aelpecyem.elementaristics.blocks.tileentity.pantheon.TileEntityDeityShrine;
import de.aelpecyem.elementaristics.misc.elements.Aspect;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public abstract class Deity {
    private int tickTimeBegin, tickTimeEnd;
    private ResourceLocation name;
    private Aspect aspect;

    public Deity(int tickTimeBegin, int tickTimeEnd, ResourceLocation name, @Nullable Aspect aspect) {
        this.tickTimeBegin = tickTimeBegin;
        this.tickTimeEnd = tickTimeEnd;
        this.name = name;
    }

    public int getTickTimeBegin() {
        return tickTimeBegin;
    }

    public int getTickTimeEnd() {
        return tickTimeEnd;
    }

    public ResourceLocation getName() {
        return name;
    }

    public Aspect getAspect() {
        return aspect;
    }

    public abstract void symbolEffect(TileEntityDeityShrine te);

    public abstract void statueEffect(TileEntityDeityShrine te);

    public abstract void sacrificeEffect(TileEntityDeityShrine te);

    public abstract void activeStatueEffect(TileEntityDeityShrine te);

}
