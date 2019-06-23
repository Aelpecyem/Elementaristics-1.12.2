package de.aelpecyem.elementaristics.blocks.tileentity.blocks.pantheon;

import de.aelpecyem.elementaristics.blocks.tileentity.BlockTileEntity;
import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityPurifier;
import de.aelpecyem.elementaristics.blocks.tileentity.energy.TileEntityEnergyStorage;
import de.aelpecyem.elementaristics.blocks.tileentity.pantheon.TileEntityDeityShrine;
import de.aelpecyem.elementaristics.items.base.artifacts.ItemChannelingTool;
import de.aelpecyem.elementaristics.misc.pantheon.Deity;
import de.aelpecyem.elementaristics.util.InventoryUtil;
import de.aelpecyem.elementaristics.util.TimeUtil;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class BlockDeityShrineBase extends BlockTileEntity<TileEntityDeityShrine> {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public Deity deity;
    public boolean isStatue;

    public BlockDeityShrineBase(String name, Deity deity, boolean isStatue) {
        super(Material.ROCK, name);
        this.deity = deity;
        this.isStatue = isStatue;
    }

    @Override
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        if (meta < 2 || meta > 5) {
            meta = 2;
        }
        return getDefaultState().withProperty(FACING, EnumFacing.getFront(meta));
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entity, ItemStack stack) {
        EnumFacing entityFacing = entity.getHorizontalFacing();

        if (!world.isRemote) {
            if (entityFacing == EnumFacing.NORTH) {
                entityFacing = EnumFacing.SOUTH;
            } else if (entityFacing == EnumFacing.EAST) {
                entityFacing = EnumFacing.WEST;
            } else if (entityFacing == EnumFacing.SOUTH) {
                entityFacing = EnumFacing.NORTH;
            } else if (entityFacing == EnumFacing.WEST) {
                entityFacing = EnumFacing.EAST;
            }

            world.setBlockState(pos, state.withProperty(FACING, entityFacing), 2);
        }
        if (world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof TileEntityDeityShrine) {
            TileEntityDeityShrine te = (TileEntityDeityShrine) world.getTileEntity(pos);
            te.deityBound = deity.getName().toString();
            te.isStatue = isStatue;
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            TileEntityDeityShrine tile = getTileEntity(worldIn, pos);
            if (playerIn.isSneaking() && !(playerIn.getHeldItem(hand).getItem() instanceof ItemChannelingTool)) {
                if (hand == EnumHand.MAIN_HAND)
                    tile.receives = !tile.receives;
            }
        }
        if (isStatue && TimeUtil.getTimeUnfalsified(worldIn.getWorldTime()) >= deity.getTickTimeBegin() && TimeUtil.getTimeUnfalsified(worldIn.getWorldTime()) <= deity.getTickTimeBegin() + 1000) {
            deity.activeStatueEffect(getTileEntity(worldIn, pos));
            deity.activeStatueEffect(getTileEntity(worldIn, pos), playerIn, hand, facing, hitX, hitY, hitZ);
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }


    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }


    @Override
    public Class<TileEntityDeityShrine> getTileEntityClass() {
        return TileEntityDeityShrine.class;
    }

    @Override
    public TileEntityDeityShrine createTileEntity(World world, IBlockState state) {
        return new TileEntityDeityShrine();
    }
}
