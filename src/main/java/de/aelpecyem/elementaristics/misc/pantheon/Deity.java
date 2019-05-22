package de.aelpecyem.elementaristics.misc.pantheon;

import de.aelpecyem.elementaristics.blocks.tileentity.pantheon.TileEntityDeityShrine;
import de.aelpecyem.elementaristics.init.Deities;
import de.aelpecyem.elementaristics.misc.elements.Aspect;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public abstract class Deity {
    private long tickTimeBegin;
    private ResourceLocation name;
    private Aspect aspect;
    private int color;

    public Deity(long tickTimeBegin, ResourceLocation name, @Nullable Aspect aspect, int color) {
        this.tickTimeBegin = tickTimeBegin;
        this.name = name;
        this.aspect = aspect;
        this.color = color;
        Deities.deities.put(name, this);
    }

    public int getColor() {
        return color;
    }

    public long getTickTimeBegin() {
        return tickTimeBegin;
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
