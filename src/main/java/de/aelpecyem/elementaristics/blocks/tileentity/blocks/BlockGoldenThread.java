package de.aelpecyem.elementaristics.blocks.tileentity.blocks;

import de.aelpecyem.elementaristics.blocks.tileentity.BlockTileEntity;
import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityGoldenThread;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;


public class BlockGoldenThread extends BlockTileEntity<TileEntityGoldenThread> {
    public BlockGoldenThread() {
        super(Material.ROCK, "block_golden_thread");
        setLightOpacity(0);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    protected boolean canSilkHarvest() {
        return false;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntityGoldenThread tile = getTileEntity(worldIn, pos);
        IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
        ItemStack stack = itemHandler.getStackInSlot(0);
        if (!stack.isEmpty()) {
            EntityItem item = new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), stack);
            worldIn.spawnEntity(item);
        }
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity) {
        if (getTileEntity(world, pos) != null) {
            if (getTileEntity(world, pos).activationStage > 0) {
                return false;
            }
        }
        return super.canEntityDestroy(state, world, pos, entity);
    }

    //might give that a higher bounding box

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntityGoldenThread te = getTileEntity(worldIn, pos);
        if (te != null) {
            te.charge++; //test only
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }

   /* @Override
    public ItemStack getItem(World world, BlockPos pos, IBlockState state) {
        return new ItemStack(item);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return super.getItemDropped(state, rand, fortune);
    }*/

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return super.canPlaceBlockAt(world, pos) && super.canPlaceBlockAt(world, pos.up());
    }

    @Override
    public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
        return false;
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public Class<TileEntityGoldenThread> getTileEntityClass() {
        return TileEntityGoldenThread.class;
    }

    @Override
    public TileEntityGoldenThread createTileEntity(World world, IBlockState state) {
        return new TileEntityGoldenThread();
    }
}
