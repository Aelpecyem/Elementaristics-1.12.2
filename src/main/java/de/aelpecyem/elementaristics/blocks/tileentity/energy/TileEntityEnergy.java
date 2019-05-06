package de.aelpecyem.elementaristics.blocks.tileentity.energy;

import de.aelpecyem.elementaristics.blocks.tileentity.IHasBoundPosition;
import de.aelpecyem.elementaristics.capability.energy.EnergyCapability;
import de.aelpecyem.elementaristics.networking.PacketHandler;
import de.aelpecyem.elementaristics.networking.tileentity.energy.EnergySync;
import de.aelpecyem.elementaristics.networking.tileentity.inventory.PacketUpdateInventory;
import de.aelpecyem.elementaristics.networking.tileentity.tick.PacketUpdateTickTime;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class TileEntityEnergy extends TileEntity implements ITickable, IHasBoundPosition {
    //todo, make a basis for all energy TEs to be bound to a generator
    public EnergyCapability storage = new EnergyCapability(getMaxEnergy(), getTransfer(0), getTransfer(1));

    public int getMaxEnergy() {
        return 1000;
    }

    /**
     * @param i 0 for input, 1, for output
     * @return
     */
    public int getTransfer(int i) {
        return 10;
    }

    public BlockPos posBound = null;
    public boolean receives = !canProvide();

    public boolean canProvide() {
        return false;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        storage.writeToNBT(compound);
        if (posBound == null) {
            posBound = pos;
        }
        compound.setLong("posBound", posBound.toLong());
        compound.setBoolean("receives", receives);
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        storage.readFromNBT(compound);
        if (posBound == null) {
            posBound = pos;
        }
        posBound = BlockPos.fromLong(compound.getLong("posBound"));
        receives = compound.getBoolean("receives");
        super.readFromNBT(compound);
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

        if (posBound == null) {
            posBound = pos;
        }
        if (!world.isRemote) {
            PacketHandler.sendToAllAround(world, pos, 64, new EnergySync(this, storage));
        }

        if (getPositionBoundTo() != null || getPositionBoundTo() != pos) {
            if (world.getTileEntity(getPositionBoundTo()) != null) {
                TileEntity te = world.getTileEntity(getPositionBoundTo());
                if (te.hasCapability(CapabilityEnergy.ENERGY, null) && getPositionBoundTo().getDistance(pos.getX(), pos.getY(), pos.getZ()) < 16) {
                    IEnergyStorage linkedTo = te.getCapability(CapabilityEnergy.ENERGY, null);
                    if (receives) {
                        if (storage.getEnergyStored() + storage.getMaxReceive() <= storage.getMaxEnergyStored())
                            storage.receiveEnergy(linkedTo.extractEnergy(storage.getMaxReceive(), false), false);
                    } else {
                        if (linkedTo.getEnergyStored() + storage.getMaxExtract() <= linkedTo.getMaxEnergyStored())
                            linkedTo.receiveEnergy(storage.extractEnergy(storage.getMaxExtract(), false), false);
                    }
                }
            }
        }

    }


    @Override
    public BlockPos getPositionBoundTo() {
        return posBound;
    }

    @Override
    public void setPositionBoundTo(BlockPos pos) {
        posBound = pos;
    }
}
