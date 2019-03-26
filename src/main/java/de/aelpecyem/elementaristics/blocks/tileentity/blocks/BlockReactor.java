package de.aelpecyem.elementaristics.blocks.tileentity.blocks;

import de.aelpecyem.elementaristics.blocks.tileentity.BlockTileEntity;
import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityReactor;
import de.aelpecyem.elementaristics.util.InventoryUtil;
import de.aelpecyem.elementaristics.util.MaganUtil;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;


public class BlockReactor extends BlockTileEntity<TileEntityReactor> {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    public BlockReactor() {
        super(Material.ROCK, "reactor");

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
        TileEntityReactor tile = getTileEntity(worldIn, pos);
        IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
        ItemStack stack = itemHandler.getStackInSlot(0);
        if (!stack.isEmpty()) {
            EntityItem item = new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), stack);
            worldIn.spawnEntity(item);
        }

        ItemStack stack2 = itemHandler.getStackInSlot(1);
        if (!stack2.isEmpty()) {
            EntityItem item = new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), stack2);
            worldIn.spawnEntity(item);
        }

        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            TileEntityReactor tile = getTileEntity(worldIn, pos);
            IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing);
            ItemStack heldItem = playerIn.getHeldItem(hand);

            if (heldItem.isEmpty()) {
                InventoryUtil.drawItemFromInventoryBackasswards(tile, tile.inventory, playerIn);
            } else {
                if (heldItem.getItem() instanceof ItemFlintAndSteel) {
                    if (!tile.inventory.getStackInSlot(0).isEmpty() && !tile.inventory.getStackInSlot(1).isEmpty() && !tile.crafting) {
                        if (!tile.crafting) {
                            if (MaganUtil.drainMaganFromClosestPlayer(pos, worldIn, 4, 20, 20, true)) {
                                tile.crafting = true;
                            }
                        }
                    }
                }

                if (tile.inventory.getStackInSlot(0).isEmpty()) {
                    playerIn.setHeldItem(hand, itemHandler.insertItem(0, heldItem, false));
                    tile.markDirty();
                } else if (tile.inventory.getStackInSlot(1).isEmpty()) {
                    playerIn.setHeldItem(hand, itemHandler.insertItem(1, heldItem, false));
                    tile.markDirty();
                }

            }

            tile.markDirty();
        }

        return true;
    }


    @Override
    public Class<TileEntityReactor> getTileEntityClass() {
        return TileEntityReactor.class;
    }

    @Override
    public TileEntityReactor createTileEntity(World world, IBlockState state) {
        return new TileEntityReactor();
    }
}
