package de.aelpecyem.elementaristics.blocks.tileentity.tile;

import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import de.aelpecyem.elementaristics.capability.player.souls.Soul;
import de.aelpecyem.elementaristics.networking.PacketHandler;
import de.aelpecyem.elementaristics.util.CapabilityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityVessel extends TileEntity implements ITickable {

    public ItemStackHandler inventory = new ItemStackHandler(5) {
        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }
    };
    public int tickCount;
    /**
     * Modes:
     * 0: Deus Ex Machina shenanigans
     * 1: Forging the Golden Thread
     */
    public int mode = 0;
    public Entity cagedEntity;
    public EntityLivingBase lastUser;

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("inventory", inventory.serializeNBT());
        compound.setInteger("tickCount", tickCount);
        compound.setInteger("mode", mode);
        cagedEntity.writeToNBT(compound);
        lastUser.writeToNBT(compound);
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        inventory.deserializeNBT(compound.getCompoundTag("inventory"));
        tickCount = compound.getInteger("tickCount");
        mode = compound.getInteger("mode");
        cagedEntity.readFromNBT(compound);
        lastUser.readFromNBT(compound);
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

    @Override
    public void update() {
        if (!world.isRemote) {
            PacketHandler.syncTile(this);
        }

        if (getUserQualification() > 0 && getUserSoul() != null) {
            if (mode == 1) { //Forging the golden thread
            /*if (isValidCultist()  && hasIngredients()){//external class for handling that
                createGoldenThread();
                //ascend?
            }*/
            }
        }
    }

    public int getUserQualification() {
        if (lastUser != null && lastUser.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
            return lastUser.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null).getPlayerAscensionStage(); //probably take in ascension route
        }
        return 0;
    }

    public Soul getUserSoul() {
        if (lastUser != null && lastUser instanceof EntityPlayer && lastUser.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
            return CapabilityUtil.getSoul((EntityPlayer) lastUser);
        }
        return null;
    }

    //adding entity is part of the block? entity must be on top, then the block is right clicked

}



