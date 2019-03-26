package de.aelpecyem.elementaristics.blocks.tileentity.blocks;

import de.aelpecyem.elementaristics.blocks.tileentity.BlockTileEntity;
import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityAltar;
import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityPurifier;
import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityReactor;
import de.aelpecyem.elementaristics.items.base.artifacts.rites.IncantationBase;
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
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;


public class BlockAltar extends BlockTileEntity<TileEntityAltar> {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    public BlockAltar() {
        super(Material.ROCK, "altar");

    }
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return new AxisAlignedBB(0, 0, 0, 1, 0.75, 1);
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
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntityAltar tile = getTileEntity(worldIn, pos);
        if (playerIn.getHeldItem(hand).getItem() instanceof IncantationBase){
            if (playerIn.isSneaking()){
                System.out.println(tile.currentRite.equals(((IncantationBase) playerIn.getHeldItem(hand).getItem()).getRite().name.toString()));
                if (tile.currentRite.equals(((IncantationBase) playerIn.getHeldItem(hand).getItem()).getRite().name.toString())) {
                    tile.currentRite = "";
                    tile.tickCount = 0;
                }
            }else {
                IncantationBase incantation = (IncantationBase) playerIn.getHeldItem(hand).getItem();
                tile.currentRite = incantation.getRite().getName().toString();
            }
        }
        return true;
    }


    @Override
    public Class<TileEntityAltar> getTileEntityClass() {
        return TileEntityAltar.class;
    }

    @Override
    public TileEntityAltar createTileEntity(World world, IBlockState state) {
        return new TileEntityAltar();
    }
}
