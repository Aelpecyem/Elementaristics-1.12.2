package de.aelpecyem.elementaristics.blocks.tileentity.tile;

import de.aelpecyem.elementaristics.capability.player.souls.Soul;
import de.aelpecyem.elementaristics.misc.elements.Aspect;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class TileEntityAltar extends TileEntity implements ITickable {

    public int tickCount;
    public String currentRite = "";
    Random random = new Random();
    int addedPower = 0;
    Map<Aspect, Integer> aspectsExt = new ConcurrentHashMap<>();
    Map<Soul, Integer> soulsExt = new ConcurrentHashMap<>();

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("tickCount", tickCount);
        compound.setString("rite", currentRite);
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        tickCount = compound.getInteger("tickCount");
        currentRite = compound.getString("rite");
        super.readFromNBT(compound);
    }

    //check IHasRiteUse for blocks, too
    @Override
    public void update() {

    }


}



