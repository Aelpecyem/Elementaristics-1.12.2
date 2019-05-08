package de.aelpecyem.elementaristics.blocks.tileentity;
import net.minecraft.util.math.BlockPos;

public interface IHasBoundPosition {
    BlockPos getPositionBoundTo();

    void setPositionBoundTo(BlockPos pos);
}
