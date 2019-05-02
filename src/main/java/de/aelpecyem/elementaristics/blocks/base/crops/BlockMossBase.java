package de.aelpecyem.elementaristics.blocks.base.crops;

import com.google.common.base.Predicate;
import de.aelpecyem.elementaristics.blocks.base.BlockBase;
import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import de.aelpecyem.elementaristics.config.Config;
import de.aelpecyem.elementaristics.entity.EntitySilverThread;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.common.util.ITeleporter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockMossBase extends BlockBase implements IShearable {
    protected boolean spreads;

    public BlockMossBase(String name, boolean spreads) {
        super(Material.PLANTS, name);
        setHardness(0);
        setResistance(0.5F);
        this.spreads = spreads;
        setSoundType(SoundType.PLANT);
        if (spreads)
            setTickRandomly(true);
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos) {
        return false;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        if (worldIn.getBlockState(pos.add(0, -1, 1)) == Blocks.COBBLESTONE) {
            worldIn.setBlockState(pos.add(0, -1, 1), Blocks.MOSSY_COBBLESTONE.getDefaultState());
        }
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        if (worldIn.getBlockState(pos.down()).getMaterial() == Material.GROUND || worldIn.getBlockState(pos.down()).getMaterial() == Material.ROCK) {
            return true;
        } else {
            return false;
        }
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
    public void observedNeighborChange(IBlockState observerState, World world, BlockPos observerPos, Block changedBlock, BlockPos changedBlockPos) {
        if (!(world.getBlockState(observerPos.down()).getMaterial() == Material.GROUND || world.getBlockState(observerPos.down()).getMaterial() == Material.ROCK)) {
            world.setBlockState(observerPos, Blocks.AIR.getDefaultState());
        }
        super.observedNeighborChange(observerState, world, observerPos, changedBlock, changedBlockPos);
    }

    @Override
    public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
        BlockPos nextPos = new BlockPos(pos.getX() + (random.nextBoolean() ? 1 : 0) * (random.nextBoolean() ? -1 : 1), pos.getY() - 1, pos.getZ() + (random.nextBoolean() ? 1 : 0) * (random.nextBoolean() ? -1 : 1));
        BlockPos upPos = new BlockPos(nextPos.getX(), nextPos.getY() + 1, nextPos.getZ());
        if (spreads) {
            if (worldIn.getBlockState(nextPos).getBlock() == Blocks.COBBLESTONE || worldIn.getBlockState(nextPos).getBlock() == Blocks.MOSSY_COBBLESTONE) {
                if (worldIn.getBlockState(upPos).getBlock() instanceof BlockAir) {
                    worldIn.setBlockState(upPos, this.getDefaultState());
                    worldIn.setBlockState(nextPos, Blocks.MOSSY_COBBLESTONE.getDefaultState());
                }
            }
        }
        super.randomTick(worldIn, pos, state, random);
    }

    @Override
    public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
        return true;
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {

    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return NULL_AABB;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return new AxisAlignedBB(0, 0, 0, 1, 0.05, 1);
    }

    @Override
    public boolean isShearable(@Nonnull ItemStack item, IBlockAccess world, BlockPos pos) {
        return true;
    }

    @Nonnull
    @Override
    public List<ItemStack> onSheared(@Nonnull ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        List<ItemStack> items = new ArrayList<>();
        items.add(new ItemStack(this));
        return items;
    }
}
