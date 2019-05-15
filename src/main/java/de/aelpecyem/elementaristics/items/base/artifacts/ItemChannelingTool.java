package de.aelpecyem.elementaristics.items.base.artifacts;


import de.aelpecyem.elementaristics.blocks.tileentity.IHasBoundPosition;
import de.aelpecyem.elementaristics.items.base.artifacts.rites.ItemAspects;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.util.MiscUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import java.util.List;

public class ItemChannelingTool extends ItemAspects {
    protected final String X_KEY = "posX";
    protected final String Y_KEY = "posY";
    protected final String Z_KEY = "posZ";

    public ItemChannelingTool() {
        super("tool_channeling", 4, false, Aspects.electricity);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey(X_KEY)) {
            tooltip.add(I18n.format("tooltip.posX") + " " + stack.getTagCompound().getInteger(X_KEY));
            tooltip.add(I18n.format("tooltip.posY") + " " + stack.getTagCompound().getInteger(Y_KEY));
            tooltip.add(I18n.format("tooltip.posZ") + " " + stack.getTagCompound().getInteger(Z_KEY));
            tooltip.add(" ");
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }



    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        if (player.isSneaking()) {
            stack.getTagCompound().setInteger(X_KEY, pos.getX());
            stack.getTagCompound().setInteger(Y_KEY, pos.getY());
            stack.getTagCompound().setInteger(Z_KEY, pos.getZ());
            if (worldIn.isRemote)
                player.sendStatusMessage(new TextComponentString(I18n.format("message.position_set") + " " + stack.getTagCompound().getInteger(X_KEY) + " " +
                        stack.getTagCompound().getInteger(Y_KEY) + " " + stack.getTagCompound().getInteger(Z_KEY)), true);
        } else {
            if (worldIn.getTileEntity(pos) != null && worldIn.getTileEntity(pos) instanceof IHasBoundPosition) {
                if (stack.getTagCompound().hasKey(X_KEY)) {
                    ((IHasBoundPosition) worldIn.getTileEntity(pos)).setPositionBoundTo(new BlockPos(stack.getTagCompound().getInteger(X_KEY),
                            stack.getTagCompound().getInteger(Y_KEY), stack.getTagCompound().getInteger(Z_KEY)));
                    if (worldIn.isRemote)
                        player.sendStatusMessage(new TextComponentString(I18n.format("message.position_set_for") + " " + stack.getTagCompound().getInteger(X_KEY) + " " +
                                stack.getTagCompound().getInteger(Y_KEY) + " " + stack.getTagCompound().getInteger(Z_KEY)), true);
                }
            }
        }
        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }
}
