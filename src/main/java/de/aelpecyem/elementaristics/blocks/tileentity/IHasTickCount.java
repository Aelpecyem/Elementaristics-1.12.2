package de.aelpecyem.elementaristics.blocks.tileentity;

import net.minecraftforge.items.ItemStackHandler;

public interface IHasTickCount {
    int getTickCount();

    void setTickCount(int tickCount);
}
