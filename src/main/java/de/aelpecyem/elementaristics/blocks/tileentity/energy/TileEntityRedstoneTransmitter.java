package de.aelpecyem.elementaristics.blocks.tileentity.energy;

import de.aelpecyem.elementaristics.blocks.tileentity.IHasBoundPosition;
import de.aelpecyem.elementaristics.capability.energy.EnergyCapability;
import de.aelpecyem.elementaristics.networking.PacketHandler;
import de.aelpecyem.elementaristics.networking.tileentity.energy.EnergySync;
import de.aelpecyem.elementaristics.networking.tileentity.pos.PosSync;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneDiode;
import net.minecraft.block.BlockRedstoneLight;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class TileEntityRedstoneTransmitter extends TileEntity implements ITickable, IHasBoundPosition {
    public BlockPos posBound = null;
    public boolean activated;

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        if (posBound == null) {
            posBound = pos;
        }
        compound.setLong("posBound", posBound.toLong());
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        if (posBound == null) {
            posBound = pos;
        }
        posBound = BlockPos.fromLong(compound.getLong("posBound"));
        super.readFromNBT(compound);
    }


    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return super.getCapability(capability, facing);
    }


    @Override
    public void update() {

        if (posBound == null) {
            posBound = pos;
        }
        if (!world.isRemote) {
            PacketHandler.sendToAllAround(world, pos, 64, new PosSync(this));
            //    PacketHandler.sendToAllAround(world, pos, 64, new );
        }
        if (this.world.isBlockPowered(pos)) {
            activated = true;
        }

        if (getPositionBoundTo() != null || getPositionBoundTo() != pos) {
            if (world.getTileEntity(getPositionBoundTo()) != null) {
                if (world.getTileEntity(getPositionBoundTo()) instanceof TileEntityRedstoneTransmitter) {
                    ((TileEntityRedstoneTransmitter) world.getTileEntity(getPositionBoundTo())).activated = activated;
                } else if (world.getBlockState(getPositionBoundTo()).canProvidePower()) {

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
