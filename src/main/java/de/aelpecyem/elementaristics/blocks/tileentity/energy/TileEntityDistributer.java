package de.aelpecyem.elementaristics.blocks.tileentity.energy;

import de.aelpecyem.elementaristics.capability.energy.EnergyCapability;
import de.aelpecyem.elementaristics.init.ModItems;
import de.aelpecyem.elementaristics.items.base.ItemEssence;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.networking.PacketHandler;
import de.aelpecyem.elementaristics.networking.tileentity.energy.generatorCombustion.PacketUpdateCombustionGenerator;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityDistributer extends TileEntity implements ITickable {
    //todo, make a basis for all energy TEs to be bound to a generator
    //todo make a basis for messages for all entities that have inventories
    public EnergyCapability storage = new EnergyCapability(100, 1);
    public int xBound, yBound, zBound;

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        storage.writeToNBT(compound);
        compound.setInteger("xBound", xBound);
        compound.setInteger("yBound", yBound);
        compound.setInteger("zBound", zBound);
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        storage.readFromNBT(compound);
        xBound = compound.getInteger("xBound");
        yBound = compound.getInteger("yBound");
        zBound = compound.getInteger("zBound");
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
        if (world.getTileEntity(new BlockPos(xBound, yBound, zBound)) != null) {
            TileEntity te = world.getTileEntity(new BlockPos(xBound, yBound, zBound));
            if (te.hasCapability(CapabilityEnergy.ENERGY, null)) {
                IEnergyStorage linkedTo = te.getCapability(CapabilityEnergy.ENERGY, null);
                storage.receiveEnergy(linkedTo.extractEnergy(1, false), false);
            }
        }
        System.out.println("Energy in distributer: " + storage.getEnergyStored());

        if (!world.isRemote) {
            //    PacketHandler.sendToAllAround(world, pos, 64, new PacketUpdateCombustionGenerator(TileEntityDistributer.this));
        }

    }


}
