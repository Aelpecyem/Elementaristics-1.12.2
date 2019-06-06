package de.aelpecyem.elementaristics.blocks.tileentity.energy;

import de.aelpecyem.elementaristics.blocks.tileentity.IHasBoundPosition;
import de.aelpecyem.elementaristics.init.ModBlocks;
import de.aelpecyem.elementaristics.networking.PacketHandler;
import de.aelpecyem.elementaristics.networking.tileentity.energy.PacketUpdateEmulator;
import de.aelpecyem.elementaristics.networking.tileentity.energy.PacketUpdateTransmitter;
import de.aelpecyem.elementaristics.networking.tileentity.pos.PosSync;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;

public class TileEntityRedstoneEmulator extends TileEntity implements ITickable, IHasBoundPosition {
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
            PacketHandler.sendToAllAround(world, pos, 64, new PacketUpdateEmulator(this));
        }


        if (isBoundToATile(this) && posBound.getDistance(pos.getX(), pos.getY(), pos.getZ()) < 40) {
            if (this.world.isBlockPowered(posBound)) {
                activated = true;
                world.getBlockState(pos).neighborChanged(world, pos, ModBlocks.block_transmitter_redstone, pos); //emulator block
            } else {
                if (world.getTileEntity(posBound) != null && world.getTileEntity(posBound) instanceof TileEntityRedstoneEmulator) {
                    if (((TileEntityRedstoneEmulator) world.getTileEntity(posBound)).activated) {
                        activated = true;
                        world.getBlockState(pos).neighborChanged(world, pos, ModBlocks.block_transmitter_redstone, pos);
                    } else {
                        activated = false;
                        world.getBlockState(pos).neighborChanged(world, pos, ModBlocks.block_transmitter_redstone, pos);
                    }
                } else {
                    activated = false;
                    world.getBlockState(pos).neighborChanged(world, pos, ModBlocks.block_transmitter_redstone, pos);
                }
            }
        }
    }


    public boolean isBoundToATile(TileEntityRedstoneEmulator tile) {
        if (tile.getPositionBoundTo() != null && !tile.getPositionBoundTo().equals(tile.pos)) {
            return true;
        }
        return false;
    }

    public boolean shouldProvideWeakPower() {
        if (activated && this.posBound != null && !this.posBound.equals(pos)) {
            return true;
        }
        return false;
    }

    @Override
    public BlockPos getPositionBoundTo() {
        return this.posBound;
    }

    @Override
    public void setPositionBoundTo(BlockPos pos) {
        this.posBound = pos;
    }

}
