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

    public static boolean areStacksEqual(ItemStack stack1, ItemStack stack2) {
        return stack1.isItemEqual(stack2) && stack1.getTagCompound() == stack2.getTagCompound();
    }

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
            if (inv.getStackInSlot(slot).isEmpty()) {
                ItemStack stack = player.getHeldItem(EnumHand.MAIN_HAND).copy();
                stack.setCount(1);
                inv.setStackInSlot(slot, stack);
                player.getHeldItem(hand).shrink(1);
            } else if (areStacksEqual(inv.getStackInSlot(slot), player.getHeldItem(hand))) {
                inv.getStackInSlot(slot).grow(1);
                player.getHeldItem(hand).shrink(1);
            }
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

    public static int hasStack(IItemHandler inv, ItemStack stack) {
        for (int i = 0; i < inv.getSlots(); i++) {
            if (stack.isItemEqual(inv.getStackInSlot(i))) {//if (areStacksEqual(inv.getStackInSlot(i), stack)){
                return i;
            }
        }
        return -1;
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

    public static int hasSpace(IItemHandler handler) {
        for (int i = 0; i < handler.getSlots(); i++) {
            if (handler.getStackInSlot(i).isEmpty()) {
                return i;
            }
        }
        return -1;
    }

    public static int hasSpace(IItemHandler handler, ItemStack stack) {
        for (int i = 0; i < handler.getSlots(); i++) {
            if (areStacksEqual(handler.getStackInSlot(i), stack) && handler.getStackInSlot(i).getCount() + stack.getCount() <= stack.getMaxStackSize() && handler.getStackInSlot(i).getCount() + stack.getCount() <= handler.getSlotLimit(i)) {
                return i;
            }
        }
        for (int i = 0; i < handler.getSlots(); i++) {
            //    System.out.println("checking for slot: " + handler.getStackInSlot(i).isEmpty());
            if (handler.getStackInSlot(i).isEmpty()) {
                return i;
            }
        }
        return -1;
    }


    public static ItemStack containsItemWithMeta(InventoryPlayer inventoryPlayer, Item item, int meta) {
        ItemStack itemstack = null;
        for (ItemStack s : inventoryPlayer.mainInventory) {
            if (s != null && s.getItem() == item && s.getMetadata() == meta) {
                itemstack = s;
                break;
            }
        }
        return itemstack;
    }

}
