package de.aelpecyem.elementaristics.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

public class InventoryUtil {
    //snatched a bit of code from Botania c:
    public static void drawItemFromInventory(TileEntity te, ItemStackHandler inv, EntityPlayer player) {
        for (int i = inv.getSlots() - 1; i >= 0; i--) {
            ItemStack stackAt = inv.getStackInSlot(i);
            if (!stackAt.isEmpty()) {
                ItemStack copy = stackAt.copy();
                ItemHandlerHelper.giveItemToPlayer(player, copy);
                inv.setStackInSlot(i, ItemStack.EMPTY);
                player.world.updateComparatorOutputLevel(te.getPos(), null);
                break;
            }
        }
    }

    public static void drawItemFromInventoryBackasswards(TileEntity te, ItemStackHandler inv, EntityPlayer player) {
        for (int i = 0; i < inv.getSlots(); i++) {
            ItemStack stackAt = inv.getStackInSlot(i);
            if (!stackAt.isEmpty()) {
                ItemStack copy = stackAt.copy();
                ItemHandlerHelper.giveItemToPlayer(player, copy);
                inv.setStackInSlot(i, ItemStack.EMPTY);
                player.world.updateComparatorOutputLevel(te.getPos(), null);
                break;
            }
        }
    }


}
