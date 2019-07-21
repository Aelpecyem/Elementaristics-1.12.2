package de.aelpecyem.elementaristics.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraftforge.items.IItemHandler;
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

    public static void insertOneItemToInventory(TileEntity te, ItemStackHandler inv, int slot, EntityPlayer player, EnumHand hand) {
        if (inv.getSlotLimit(slot) > inv.getStackInSlot(slot).getCount()) {
            inv.setStackInSlot(slot, new ItemStack(player.getHeldItem(hand).getItem(), 1 + inv.getStackInSlot(slot).getCount(), player.getHeldItem(hand).getMetadata()));
            player.getHeldItem(hand).shrink(1);
            player.world.updateComparatorOutputLevel(te.getPos(), null);
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

    public static ItemStack containsItem(InventoryPlayer inventoryPlayer, Item item) {
        ItemStack itemstack = null;
        for (ItemStack s : inventoryPlayer.mainInventory) {
            if (s != null && s.getItem() == item) {
                itemstack = s;
                break;
            }
        }
        return itemstack;
    }


}
