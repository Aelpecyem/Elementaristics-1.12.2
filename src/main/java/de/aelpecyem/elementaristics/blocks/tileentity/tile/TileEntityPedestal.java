package de.aelpecyem.elementaristics.blocks.tileentity.tile;

import de.aelpecyem.elementaristics.networking.PacketHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Random;

public class TileEntityPedestal extends TileEntity implements ITickable {

    public ItemStackHandler inventory = new ItemStackHandler(1) {


        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }
    };
    Random random = new Random();

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("inventory", inventory.serializeNBT());
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        inventory.deserializeNBT(compound.getCompoundTag("inventory"));
        super.readFromNBT(compound);
    }


    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }


    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? (T) inventory : super.getCapability(capability, facing);
    }

    public ItemStack getItem() {
        return inventory.getStackInSlot(0);
    }


    @Override
    public void update() {
        if (!world.isRemote) {
            PacketHandler.syncTile(this);
        }
    }
}
