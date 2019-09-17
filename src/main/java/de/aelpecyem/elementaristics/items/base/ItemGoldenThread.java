package de.aelpecyem.elementaristics.items.base;

import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityGoldenThread;
import de.aelpecyem.elementaristics.init.ModBlocks;
import de.aelpecyem.elementaristics.misc.elements.Aspect;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.networking.PacketHandler;
import de.aelpecyem.elementaristics.networking.other.PacketMarkBlock;
import de.aelpecyem.elementaristics.networking.player.PacketMessage;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
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
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing face, float hitX, float hitY, float hitZ) {
        BlockPos pos0 = world.getBlockState(pos).getBlock().isReplaceable(world, pos) ? pos : pos.offset(face);
        ItemStack stack = player.getHeldItem(hand);
        if (hasSpace(world, pos0, player) && player.canPlayerEdit(pos0, face, stack) && world.mayPlace(world.getBlockState(pos0).getBlock(), pos0, false, face, player) && ModBlocks.block_golden_thread.canPlaceBlockAt(world, pos0)) {
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

    public static boolean hasSpace(World world, BlockPos pos, @Nullable EntityPlayer player) {
        boolean flag = true;
        for (int x = -7; x < 7; x++) {
            for (int y = 0; y < 6; y++) {
                for (int z = -7; z < 7; z++) {
                    if (!(pos == pos.add(x, y, z)) && !(world.getBlockState(pos.add(x, y, z)).getMaterial().isReplaceable() || world.getBlockState(pos.add(x, y, z)).getBlock() == Blocks.AIR)) {
                        if (!world.isRemote) {
                            if (player != null) {
                                PacketHandler.sendTo(player, new PacketMarkBlock(pos.add(x, y, z)));
                                PacketHandler.sendTo(player, new PacketMessage("message.golden_thread_space.not_enough", true));
                            }
                        }
                        flag = false;
                    }
                }
            }
        }
        return flag; //will check if a huge area is free
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey(NBTKEY_ASPECT)) {
            tooltip.add(I18n.format("tooltip.golden_thread.aspect") + " " + getAspect(stack).getLocalizedName());
        }
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
            stack.getTagCompound().setInteger(NBTKEY_ASPECT, Aspects.getRandomPrimal().getId());
        }
        return true;
    }
}
