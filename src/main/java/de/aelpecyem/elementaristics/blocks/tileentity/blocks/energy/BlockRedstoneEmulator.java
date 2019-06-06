package de.aelpecyem.elementaristics.blocks.tileentity.blocks.energy;

import de.aelpecyem.elementaristics.blocks.tileentity.BlockTileEntity;
import de.aelpecyem.elementaristics.blocks.tileentity.energy.TileEntityRedstoneEmulator;
import de.aelpecyem.elementaristics.blocks.tileentity.energy.TileEntityRedstoneTransmitter;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;


public class BlockRedstoneEmulator extends BlockTileEntity<TileEntityRedstoneEmulator> {
    public static final PropertyBool POWERED = PropertyBool.create("powered");

    public BlockRedstoneEmulator() {
        super(Material.ROCK, "block_emulator_redstone");
        this.setDefaultState(this.blockState.getBaseState().withProperty(POWERED, false));
    }


    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(POWERED).booleanValue() ? 1 : 0;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(POWERED, meta == 1 ? true : false);
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (getTileEntity(worldIn, pos) != null) {
            boolean activated = getTileEntity(worldIn, pos).activated;
            BlockPos posTo = getTileEntity(worldIn, pos).posBound;
            worldIn.setBlockState(pos, getDefaultState().withProperty(POWERED, getTileEntity(worldIn, pos).activated), 2);
            getTileEntity(worldIn, pos).activated = activated;
            getTileEntity(worldIn, pos).posBound = posTo;
            if (fromPos.equals(pos)) {
                worldIn.getBlockState(pos.add(0, 1, 0)).neighborChanged(worldIn, pos.add(0, 1, 0), worldIn.getBlockState(pos.add(0, 1, 0)).getBlock(), pos);
                worldIn.getBlockState(pos.add(0, -1, 0)).neighborChanged(worldIn, pos.add(0, -1, 0), worldIn.getBlockState(pos.add(0, 1, 0)).getBlock(), pos);
                worldIn.getBlockState(pos.add(1, 0, 0)).neighborChanged(worldIn, pos.add(1, 0, 0), worldIn.getBlockState(pos.add(0, 1, 0)).getBlock(), pos);
                worldIn.getBlockState(pos.add(0, 0, 1)).neighborChanged(worldIn, pos.add(0, 0, 1), worldIn.getBlockState(pos.add(0, 1, 0)).getBlock(), pos);
                worldIn.getBlockState(pos.add(-1, 0, 0)).neighborChanged(worldIn, pos.add(-1, 0, 0), worldIn.getBlockState(pos.add(0, 1, 0)).getBlock(), pos);
                worldIn.getBlockState(pos.add(0, 0, -1)).neighborChanged(worldIn, pos.add(0, 0, -1), worldIn.getBlockState(pos.add(0, 1, 0)).getBlock(), pos);

            }
        }
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
    }

    @Override
    public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable EnumFacing side) {
        return true;
    }

    @Override
    public boolean shouldCheckWeakPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        return false;
    }

    @Override
    public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return getTileEntity(blockAccess, pos).shouldProvideWeakPower() ? 15 : 0; //maybe remove this
    }

    public boolean canProvidePower(IBlockState state) {
        return true;
    }

    @Override
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, POWERED);
    }


    @Override
    public Class<TileEntityRedstoneEmulator> getTileEntityClass() {
        return TileEntityRedstoneEmulator.class;
    }

    @Override
    public TileEntityRedstoneEmulator createTileEntity(World world, IBlockState state) {
        return new TileEntityRedstoneEmulator();
    }
}
