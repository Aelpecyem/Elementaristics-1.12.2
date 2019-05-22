package de.aelpecyem.elementaristics.blocks.tileentity.blocks;

import de.aelpecyem.elementaristics.blocks.tileentity.BlockTileEntity;
import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityConcentrator;
import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityInfusionBasin;
import de.aelpecyem.elementaristics.items.base.ItemEssence;
import de.aelpecyem.elementaristics.items.base.artifacts.rites.ItemAspects;
import de.aelpecyem.elementaristics.items.base.artifacts.rites.materials.ItemWaterPurest;
import de.aelpecyem.elementaristics.util.InventoryUtil;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;
import java.util.List;


public class BlockBasin extends BlockTileEntity<TileEntityInfusionBasin> {
    private static double corner1 = 0.0625D;
    private static double corner2 = 1 - 0.0625D;
    private static double corner3 = 2 * 0.0625D;
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    protected static final AxisAlignedBB AABB_WALL_NORTH = new AxisAlignedBB(corner1, 0.1875D, corner1, corner2, 1.0D, corner3);
    protected static final AxisAlignedBB AABB_WALL_SOUTH = new AxisAlignedBB(corner1, 0.1875D, corner2, corner2, 1.0D, corner2);
    protected static final AxisAlignedBB AABB_WALL_EAST = new AxisAlignedBB(corner2, 0.1875D, corner1, corner2, 1.0D, corner2);
    protected static final AxisAlignedBB AABB_WALL_WEST = new AxisAlignedBB(corner1, 0.1875D, corner1, corner3, 1.0D, corner2);

    protected static final AxisAlignedBB AABB_BASE = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1D, 0.1875D, 1.0D);

    public BlockBasin() {
        super(Material.ROCK, "basin_infusion");
    }

    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_BASE);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_WEST);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_NORTH);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_EAST);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_SOUTH);
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
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
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
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
    public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
        return layer == BlockRenderLayer.CUTOUT_MIPPED;
    }


    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntityInfusionBasin tile = getTileEntity(worldIn, pos);
        IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
        ItemStack stack = itemHandler.getStackInSlot(0);
        if (!stack.isEmpty()) {
            EntityItem item = new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), stack);
            worldIn.spawnEntity(item);
        }
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
        return FULL_BLOCK_AABB;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {

            TileEntityInfusionBasin tile = getTileEntity(worldIn, pos);
            IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing);
            ItemStack heldItem = playerIn.getHeldItem(hand);
            if (heldItem.getItem() instanceof ItemEssence) {
                if (tile.fillCount > 0 && tile.aspectIDs.size() < 10) {
                    tile.addAspects(heldItem);
                    heldItem.shrink(1);
                } else {
                    if (tile.aspectIDs.size() >= 10) {
                        tile.aspectIDs.remove(0);
                        tile.addAspects(heldItem);
                        heldItem.shrink(1);
                        worldIn.playSound(null, pos, SoundEvents.ENTITY_GENERIC_SPLASH, SoundCategory.AMBIENT, 1, 1);
                        tile.markDirty();
                    }
                }
            } else if (heldItem.isEmpty()) {
                InventoryUtil.drawItemFromInventory(tile, tile.inventory, playerIn);
            } else {
                if (heldItem.getItem() instanceof ItemWaterPurest) {
                    if (tile.fillCount < 1) {
                        tile.fillCount = 4;
                        heldItem.shrink(1);
                        worldIn.playSound(null, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.AMBIENT, 1, 1);
                        tile.markDirty();
                    }
                } else if (tile.inventory.getStackInSlot(0).isEmpty()) {
                    playerIn.setHeldItem(hand, itemHandler.insertItem(0, heldItem, false));
                    tile.markDirty();
                }

            }

            tile.markDirty();
        }

        return true;
    }


    @Override
    public Class<TileEntityInfusionBasin> getTileEntityClass() {
        return TileEntityInfusionBasin.class;
    }

    @Override
    public TileEntityInfusionBasin createTileEntity(World world, IBlockState state) {
        return new TileEntityInfusionBasin();
    }
}
