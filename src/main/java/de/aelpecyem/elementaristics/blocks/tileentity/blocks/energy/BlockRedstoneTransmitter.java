package de.aelpecyem.elementaristics.blocks.tileentity.blocks.energy;

import de.aelpecyem.elementaristics.blocks.tileentity.BlockTileEntity;
import de.aelpecyem.elementaristics.blocks.tileentity.energy.TileEntityGeneratorArcaneCombustion;
import de.aelpecyem.elementaristics.blocks.tileentity.energy.TileEntityRedstoneTransmitter;
import de.aelpecyem.elementaristics.items.base.ItemEssence;
import de.aelpecyem.elementaristics.items.base.artifacts.ItemChannelingTool;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.util.InventoryUtil;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;


public class BlockRedstoneTransmitter extends BlockTileEntity<TileEntityRedstoneTransmitter> {
    public static final PropertyBool POWERED = PropertyBool.create("powered");
    public BlockRedstoneTransmitter() {
        super(Material.ROCK, "block_transmitter_redstone");
        this.setDefaultState(this.blockState.getBaseState().withProperty(POWERED, false));
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        if (getTileEntity(worldIn, pos) != null) {
            TileEntityRedstoneTransmitter te = getTileEntity(worldIn, pos);
            if (te.isBoundToATile(te)) {
                if (worldIn.getTileEntity(te.posBound) instanceof TileEntityRedstoneTransmitter) {
                    TileEntityRedstoneTransmitter tileBound = (TileEntityRedstoneTransmitter) worldIn.getTileEntity(te.posBound);
                    tileBound.posBoundFrom = tileBound.getPos();
                }
            }
        }
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public int tickRate(World worldIn) {
        return 2;
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
            BlockPos posFrom = getTileEntity(worldIn, pos).posBoundFrom;
            BlockPos posTo = getTileEntity(worldIn, pos).posBound;
            worldIn.setBlockState(pos, getDefaultState().withProperty(POWERED, getTileEntity(worldIn, pos).activated), 2);
            getTileEntity(worldIn, pos).activated = activated;
            getTileEntity(worldIn, pos).posBoundFrom = posFrom;
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
    public Class<TileEntityRedstoneTransmitter> getTileEntityClass() {
        return TileEntityRedstoneTransmitter.class;
    }

    @Override
    public TileEntityRedstoneTransmitter createTileEntity(World world, IBlockState state) {
        return new TileEntityRedstoneTransmitter();
    }
}
