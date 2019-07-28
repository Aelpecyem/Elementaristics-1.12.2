package de.aelpecyem.elementaristics.blocks.tileentity.blocks.pantheon;

import de.aelpecyem.elementaristics.blocks.tileentity.BlockTileEntity;
import de.aelpecyem.elementaristics.blocks.tileentity.pantheon.TileEntityDeityShrine;
import de.aelpecyem.elementaristics.init.Deities;
import de.aelpecyem.elementaristics.misc.pantheon.Deity;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
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
