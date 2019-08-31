package de.aelpecyem.elementaristics.items.base;

import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityGoldenThread;
import de.aelpecyem.elementaristics.init.ModBlocks;
import de.aelpecyem.elementaristics.misc.elements.Aspect;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemGoldenThread extends ItemBase {
    private static final String NBTKEY_ASPECT = "aspect";

    public ItemGoldenThread() {
        super("item_golden_thread");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        setAspect(playerIn.getHeldItem(handIn), Aspects.fire);
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing face, float hitX, float hitY, float hitZ) {
        BlockPos pos0 = world.getBlockState(pos).getBlock().isReplaceable(world, pos) ? pos : pos.offset(face);
        ItemStack stack = player.getHeldItem(hand);
        if (hasSpace(pos0) && player.canPlayerEdit(pos0, face, stack) && world.mayPlace(world.getBlockState(pos0).getBlock(), pos0, false, face, player) && ModBlocks.block_golden_thread.canPlaceBlockAt(world, pos0)) {
            System.out.println("yes");
            world.setBlockState(pos0, ModBlocks.block_golden_thread.getStateForPlacement(world, pos, face, hitX, hitY, hitZ, 0, player, hand));
            TileEntity tile = world.getTileEntity(pos0);
            if (tile instanceof TileEntityGoldenThread) {
                ((TileEntityGoldenThread) tile).setAspect(getAspect(stack).getId());
                ((TileEntityGoldenThread) tile).inventory.insertItem(0, stack.copy().splitStack(1), false);
            }
            stack.shrink(1);
            world.playSound(null, pos, ModBlocks.block_golden_thread.getSoundType().getPlaceSound(), SoundCategory.BLOCKS, 1, 1);
            return EnumActionResult.SUCCESS;
        }
        return super.onItemUse(player, world, pos, hand, face, hitX, hitY, hitZ);
    }

    public boolean hasSpace(BlockPos pos) {
        return true; //will check if a huge area is free
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18n.format("tooltip.golden_thread.aspect") + " " + getAspect(stack).getLocalizedName());
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    public void setAspect(ItemStack stack, Aspect aspect) {
        if (setUp(stack)) {
            stack.getTagCompound().setInteger(NBTKEY_ASPECT, aspect.getId());
        }
    }

    public Aspect getAspect(ItemStack stack) {
        if (setUp(stack)) {
            return Aspects.getElementById(stack.getTagCompound().getInteger(NBTKEY_ASPECT));
        }
        return Aspects.magan;
    }

    public boolean setUp(ItemStack stack) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        if (!stack.getTagCompound().hasKey(NBTKEY_ASPECT)) {
            stack.getTagCompound().setInteger(NBTKEY_ASPECT, 15);
        }
        return true;
    }
}
