package de.aelpecyem.elementaristics.blocks.tileentity.tile.energy;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

public class TileEntityEnergyStorage extends TileEntityEnergy implements ITickable {

    @Override
    public int getMaxEnergy() {
        return 50000;
    }

    @Override
    public int getTransfer(int i) {
        return 10;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
    }

    @Override
    public void update() {
        super.update();
    }


}
