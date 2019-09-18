package de.aelpecyem.elementaristics.blocks.tileentity.blocks.pantheon;

import de.aelpecyem.elementaristics.init.Deities;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockDeityWitch extends BlockDeityShrineBase {
    public BlockDeityWitch(boolean isStatue) {
        super(isStatue ? "shrine_witch":"symbol_witch", Deities.deityWitch, isStatue);
    }

    @Override
    public float getEnchantPowerBonus(World world, BlockPos pos) {
        return isStatue ? 30 : 10;
    }
}
