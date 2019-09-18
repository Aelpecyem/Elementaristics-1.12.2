package de.aelpecyem.elementaristics.items.base.artifacts;


import com.mojang.realmsclient.gui.ChatFormatting;
import de.aelpecyem.elementaristics.blocks.tileentity.tile.pantheon.TileEntityDeityShrine;
import de.aelpecyem.elementaristics.entity.nexus.EntityDimensionalNexus;
import de.aelpecyem.elementaristics.init.Deities;
import de.aelpecyem.elementaristics.items.base.artifacts.rites.ItemAspects;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.util.TimeUtil;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import java.util.List;

public class ItemEyeSplendor extends ItemAspects {

    public ItemEyeSplendor() {
        super("splendor_eye", 4, false, Aspects.light);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (worldIn.isRemote) {
            if (playerIn.rayTrace(10, 0).getBlockPos() != null) {
                if (worldIn.getTileEntity(playerIn.rayTrace(10, 0).getBlockPos()) instanceof TileEntityDeityShrine) {
                    return super.onItemRightClick(worldIn, playerIn, handIn);
                }
            }
            if (playerIn.isSneaking()) {
                playerIn.sendStatusMessage(new TextComponentString(ChatFormatting.YELLOW + I18n.format("message.deity_tick.name") + " " + String.valueOf(TimeUtil.getTimeUnfalsified(worldIn.provider.getWorldTime()))), false);
                playerIn.sendStatusMessage(new TextComponentString(ChatFormatting.YELLOW + I18n.format("message.deity_hour.name") + " " + String.valueOf(TimeUtil.getHourForTimeBegin(TimeUtil.getTimeUnfalsified(worldIn.provider.getWorldTime())))), false);

            }
            if (Deities.getDeityByWorldTime(TimeUtil.getTimeUnfalsified(worldIn.provider.getWorldTime())) != null) {
                playerIn.sendStatusMessage(new TextComponentString(ChatFormatting.GOLD + I18n.format("message.deity_active_current.name") + " " + I18n.format(Deities.getDeityByWorldTime(TimeUtil.getTimeUnfalsified(worldIn.provider.getWorldTime())).getName().toString())), false);
            } else
                playerIn.sendStatusMessage(new TextComponentString(ChatFormatting.GOLD + I18n.format("message.deity_active_none.name")), false);

        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (worldIn.getTileEntity(pos) instanceof TileEntityDeityShrine) {
            TileEntityDeityShrine te = (TileEntityDeityShrine) worldIn.getTileEntity(pos);
            if (worldIn.isRemote) {
                player.sendStatusMessage(new TextComponentString(ChatFormatting.YELLOW + I18n.format("message.deity_belong.name") + " " + I18n.format(te.deityBound)), false);
                player.sendStatusMessage(new TextComponentString(ChatFormatting.YELLOW + I18n.format("message.deity_active.name") + " " + Deities.deities.get(new ResourceLocation(te.deityBound)).getTickTimeBegin()), false);
            }
            List<EntityDimensionalNexus> nexuses = worldIn.getEntitiesWithinAABB(EntityDimensionalNexus.class, Block.FULL_BLOCK_AABB.grow(20).offset(pos));
            if (!nexuses.isEmpty()) {
                te.nexus = nexuses.get(0).getUniqueID().toString();
            }
        }
        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }
}
