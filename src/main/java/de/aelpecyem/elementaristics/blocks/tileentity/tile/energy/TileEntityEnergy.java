package de.aelpecyem.elementaristics.blocks.tileentity.tile.energy;

import de.aelpecyem.elementaristics.blocks.tileentity.IHasBoundPosition;
import de.aelpecyem.elementaristics.capability.energy.EnergyCapability;
import de.aelpecyem.elementaristics.networking.PacketHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class TileEntityEnergy extends TileEntity implements ITickable, IHasBoundPosition {
    public EnergyCapability storage = new EnergyCapability(getMaxEnergy(), getTransfer(0), getTransfer(1));

    public int getMaxEnergy() {
        return 1000;
    }

    /**
     * @param i 0 for input, 1, for output
     * @return
     */
    public int getTransfer(int i) {
        return 20;
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
            PacketHandler.syncTile(this);
        }

        if (posBound != null || posBound != pos) {
            if (world.getTileEntity(posBound) != null) {
                TileEntity te = world.getTileEntity(posBound);
                if (te.hasCapability(CapabilityEnergy.ENERGY, null) && posBound.getDistance(pos.getX(), pos.getY(), pos.getZ()) < 18) {
                    IEnergyStorage linkedTo = te.getCapability(CapabilityEnergy.ENERGY, null);
                    if (receives) {
                        storage.receiveEnergy(linkedTo.extractEnergy(storage.getMaxReceive(), false), false);
                    } else {
                        linkedTo.receiveEnergy(storage.extractEnergy(storage.getMaxExtract(), false), false);
                    }
                }
            }
        }

    }

    @Override
    public BlockPos getBoundPosition() {
        return posBound;
    }

    @Override
    public void setBoundPosition(BlockPos pos) {
        this.posBound = pos;
    }
}
