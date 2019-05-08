package de.aelpecyem.elementaristics.blocks.tileentity.pantheon;

import de.aelpecyem.elementaristics.blocks.tileentity.IHasTickCount;
import de.aelpecyem.elementaristics.blocks.tileentity.energy.TileEntityEnergy;
import de.aelpecyem.elementaristics.capability.energy.EnergyCapability;
import de.aelpecyem.elementaristics.misc.pantheon.Deities;
import de.aelpecyem.elementaristics.misc.pantheon.Deity;
import de.aelpecyem.elementaristics.networking.PacketHandler;
import de.aelpecyem.elementaristics.networking.tileentity.deities.PacketUpdateDeity;
import de.aelpecyem.elementaristics.networking.tileentity.energy.EnergySync;
import de.aelpecyem.elementaristics.networking.tileentity.tick.PacketUpdateTickTime;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.*;

public class TileEntityDeityShrine extends TileEntityEnergy implements ITickable, IHasTickCount {
    public int tickCount;
    public String deityBound;
    public boolean isStatue;
    public boolean unusedBool;
    public float unusedFloat;
    public int unusedInt;
    public String unusedString;

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        storage.writeToNBT(compound);
        compound.setInteger("tickCount", tickCount);

        compound.setString("deityBound", deityBound);
        compound.setBoolean("isStatue", isStatue);

        compound.setBoolean("unusedBool", unusedBool);
        compound.setFloat("unusedFloat", unusedFloat);
        compound.setInteger("unusedInt", unusedInt);
        compound.setString("unusedString", unusedString);
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        storage.readFromNBT(compound);
        tickCount = compound.getInteger("tickCount");

        deityBound = compound.getString("deityBound");
        isStatue = compound.getBoolean("isStatue");

        unusedBool = compound.getBoolean("unusedBool");
        unusedFloat = compound.getFloat("unusedFloat");
        unusedInt = compound.getInteger("unusedInt");
        unusedString = compound.getString("unusedString");
        super.readFromNBT(compound);
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(getPos(), getPos().add(1, 2, 1));
    }


    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == CapabilityEnergy.ENERGY || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return capability == CapabilityEnergy.ENERGY ? (T) storage : super.getCapability(capability, facing);
    }


    @Override
    public void update() {
        super.update();
        if (!world.isRemote) {
            PacketHandler.sendToAllAround(world, pos, 64, new PacketUpdateTickTime(this, tickCount));
            PacketHandler.sendToAllAround(world, pos, 64, new PacketUpdateDeity(this));
        }
        Deity deityActive = Deities.deities.get(new ResourceLocation(deityBound));
        if (world.getWorldTime() >= deityActive.getTickTimeBegin() && world.getWorldTime() <= deityActive.getTickTimeEnd()) {
            if (isStatue) {
                deityActive.statueEffect(this);
            } else {
                deityActive.symbolEffect(this);
            }
        }
      /*  if (tickCount <= 0) {
            if (this.storage.canReceive() && inventory.getStackInSlot(0).isItemEqual(new ItemStack(ModItems.essence, 1, Aspects.fire.getId()))) {
                inventory.getStackInSlot(0).shrink(1);
                tickCount += 400;
            }
        } else {
            this.storage.receiveEnergy(1, false);
            tickCount--;
        }*/
        //   System.out.println("Energy: " + storage.getEnergyStored());
    }

    @Override
    public int getTickCount() {
        return tickCount;
    }

    @Override
    public void setTickCount(int tickCount) {
        this.tickCount = tickCount;
    }
}



