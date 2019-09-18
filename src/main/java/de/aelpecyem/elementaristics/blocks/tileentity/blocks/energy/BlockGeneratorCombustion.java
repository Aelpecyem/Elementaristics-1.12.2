package de.aelpecyem.elementaristics.blocks.tileentity.blocks.energy;

import de.aelpecyem.elementaristics.blocks.tileentity.BlockTileEntity;
import de.aelpecyem.elementaristics.blocks.tileentity.tile.energy.TileEntityGeneratorArcaneCombustion;
import de.aelpecyem.elementaristics.items.base.ItemEssence;
import de.aelpecyem.elementaristics.items.base.artifacts.ItemChannelingTool;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.util.InventoryUtil;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
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

import javax.annotation.Nullable;
import java.util.List;


public class BlockGeneratorCombustion extends BlockTileEntity<TileEntityGeneratorArcaneCombustion> {

    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    protected static final AxisAlignedBB AABB_WALL_1 = new AxisAlignedBB(0, 2F / 16F, 0, 2F / 16F, 1.0D - 4F / 16F, 1);
    protected static final AxisAlignedBB AABB_WALL_2 = new AxisAlignedBB(2F / 16F, 2F / 16F, 0, 1, 1.0D - 4F / 16F, 2F / 16F);
    protected static final AxisAlignedBB AABB_WALL_3 = new AxisAlignedBB(1, 2F / 16F, 1, 1 - 2F / 16F, 1.0D - 4F / 16F, 0);
    protected static final AxisAlignedBB AABB_WALL_4 = new AxisAlignedBB(1 - 2F / 16F, 0.1875D, 1, 0, 1.0D - 4F / 16F, 1 - 2F / 16F);

    protected static final AxisAlignedBB AABB_BASE = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1D, 2F / 16F, 1.0D);


    public BlockGeneratorCombustion() {
        super(Material.ROCK, "generator_combustion");

    }

    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_BASE);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_1);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_2);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_3);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_4);
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return FULL_BLOCK_AABB;
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
        return FULL_BLOCK_AABB;
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
    }


    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntityGeneratorArcaneCombustion tile = getTileEntity(worldIn, pos);
        IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
        ItemStack stack = itemHandler.getStackInSlot(0);
        if (!stack.isEmpty()) {
            EntityItem item = new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), stack);
            worldIn.spawnEntity(item);
        }
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            TileEntityGeneratorArcaneCombustion tile = getTileEntity(worldIn, pos);
            IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing);

            ItemStack heldItem = playerIn.getHeldItem(hand);

            if (heldItem.getItem() instanceof ItemChannelingTool) {
                return false;
            }
            if (heldItem.isEmpty()) {
                InventoryUtil.drawItemFromInventory(tile, tile.inventory, playerIn);

            } else {
                if (tile.inventory.getStackInSlot(0).isEmpty() && heldItem.getItem() instanceof ItemEssence && heldItem.getMetadata() == Aspects.fire.getId()) {
                    playerIn.setHeldItem(hand, itemHandler.insertItem(0, heldItem, false));
                    tile.markDirty();
                }

            }

        }
        return true;
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
    public Class<TileEntityGeneratorArcaneCombustion> getTileEntityClass() {
        return TileEntityGeneratorArcaneCombustion.class;
    }

    @Override
    public TileEntityGeneratorArcaneCombustion createTileEntity(World world, IBlockState state) {
        return new TileEntityGeneratorArcaneCombustion();
    }
}
