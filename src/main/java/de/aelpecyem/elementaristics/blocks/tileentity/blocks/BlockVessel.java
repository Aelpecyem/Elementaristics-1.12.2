package de.aelpecyem.elementaristics.blocks.tileentity.blocks;

import de.aelpecyem.elementaristics.blocks.tileentity.BlockTileEntity;
import de.aelpecyem.elementaristics.blocks.tileentity.tile.TileEntityVessel;
import de.aelpecyem.elementaristics.items.base.artifacts.ItemChannelingTool;
import de.aelpecyem.elementaristics.util.InventoryUtil;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
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

import java.util.List;


public class BlockVessel extends BlockTileEntity<TileEntityVessel> {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    public BlockVessel() {
        super(Material.ROCK, "vessel");

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
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return new AxisAlignedBB(0, 0, 0, 1, 0.5, 1);
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
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntityVessel tile = getTileEntity(worldIn, pos);
        IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            ItemStack stackDropped = itemHandler.getStackInSlot(i);
            if (!stackDropped.isEmpty()) {
                EntityItem item = new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), stackDropped);
                worldIn.spawnEntity(item);
            }
        }
        if (tile.getCagedEntity() != null) {
            worldIn.spawnEntity(tile.getCagedEntity());
            tile.clearCage();
        }
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            TileEntityVessel tile = getTileEntity(worldIn, pos);
            tile.setLastUser(playerIn);
            ItemStack heldItem = playerIn.getHeldItem(hand);

            if (heldItem.isEmpty()) {
                if (!playerIn.isSneaking())
                    InventoryUtil.drawItemFromInventoryBackasswards(tile, tile.inventory, playerIn);
                else {
                    EntityLiving entityIn = getEntityOnTop(worldIn, pos, state); //for a test
                    if (entityIn != null && tile.getCagedEntity() == null) {
                        tile.absorbEntity(entityIn);
                    } else if (tile.getCagedEntity() != null) {
                        worldIn.spawnEntity(tile.getCagedEntity());
                        tile.clearCage();
                    }
                }
            } else {
                if (!(playerIn.getHeldItem(hand).getItem() instanceof ItemChannelingTool))
                    InventoryUtil.insertItems(tile, tile.inventory, playerIn, hand);
            }
            tile.markDirty();
        }
        return true;
    }

    public EntityLiving getEntityOnTop(World world, BlockPos pos, IBlockState state) {
        List<EntityLiving> onTopList = world.getEntitiesWithinAABB(EntityLiving.class, state.getCollisionBoundingBox(world, pos).offset(pos).grow(-0.1, 1, -0.1));
        if (!onTopList.isEmpty())
            return onTopList.get(0);
        return null;
    }

    @Override
    public Class<TileEntityVessel> getTileEntityClass() {
        return TileEntityVessel.class;
    }

    @Override
    public TileEntityVessel createTileEntity(World world, IBlockState state) {
        return new TileEntityVessel();
    }
}
