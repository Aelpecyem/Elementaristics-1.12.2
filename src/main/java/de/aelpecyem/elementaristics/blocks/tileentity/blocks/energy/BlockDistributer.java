package de.aelpecyem.elementaristics.blocks.tileentity.blocks.energy;

import de.aelpecyem.elementaristics.blocks.tileentity.BlockTileEntity;
import de.aelpecyem.elementaristics.blocks.tileentity.energy.TileEntityDistributer;
import de.aelpecyem.elementaristics.blocks.tileentity.energy.TileEntityGeneratorArcaneCombustion;
import de.aelpecyem.elementaristics.util.InventoryUtil;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;


public class BlockDistributer extends BlockTileEntity<TileEntityDistributer> {

    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    public BlockDistributer() {
        super(Material.ROCK, "block_distributer");

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
            } else if (entityFacing == EnumFacing.DOWN) {
                entityFacing = EnumFacing.UP;
            } else if (entityFacing == EnumFacing.UP) {
                entityFacing = EnumFacing.DOWN;
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
    public Class<TileEntityDistributer> getTileEntityClass() {
        return TileEntityDistributer.class;
    }

    @Override
    public TileEntityDistributer createTileEntity(World world, IBlockState state) {
        return new TileEntityDistributer();
    }
}
