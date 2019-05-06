package de.aelpecyem.elementaristics.blocks.tileentity;

import de.aelpecyem.elementaristics.blocks.tileentity.blocks.energy.BlockDistributer;
import net.minecraft.util.math.BlockPos;

public interface IHasBoundPosition {
    BlockPos getPositionBoundTo();

    void setPositionBoundTo(BlockPos pos);
}
