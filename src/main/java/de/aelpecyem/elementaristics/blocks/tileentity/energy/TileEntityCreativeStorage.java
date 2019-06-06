package de.aelpecyem.elementaristics.blocks.tileentity.energy;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

public class TileEntityCreativeStorage extends TileEntityEnergy implements ITickable {

    @Override
    public int getMaxEnergy() {
        return 100000;
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
        storage.setEnergy(getMaxEnergy());
        super.update();
    }


}
