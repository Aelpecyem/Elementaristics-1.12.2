package de.aelpecyem.elementaristics.blocks.tileentity.tile.energy;

import de.aelpecyem.elementaristics.blocks.tileentity.IHasBoundPosition;
import de.aelpecyem.elementaristics.init.ModBlocks;
import de.aelpecyem.elementaristics.networking.PacketHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;

public class TileEntityRedstoneTransmitter extends TileEntity implements ITickable, IHasBoundPosition {
    public BlockPos posBound = null;
    public BlockPos posBoundFrom = pos;
    public boolean activated;

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        if (posBound == null) {
            posBound = pos;
        }
        if (posBoundFrom == null) {
            posBoundFrom = pos;
        }
        compound.setLong("posBound", posBound.toLong());
        compound.setLong("posBoundFrom", posBoundFrom.toLong());
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        if (posBound == null) {
            posBound = pos;
        }
        if (posBoundFrom == null) {
            posBoundFrom = pos;
        }
        posBound = BlockPos.fromLong(compound.getLong("posBound"));
        posBoundFrom = BlockPos.fromLong(compound.getLong("posBoundFrom"));
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
            PacketHandler.syncTile(this);
        }

        if ((this.world.isBlockPowered(pos) && isBoundToATile(this)) || ((this.world.getTileEntity(posBoundFrom) != null && !posBoundFrom.equals(pos) && this.world.getTileEntity(posBoundFrom) instanceof TileEntityRedstoneTransmitter && ((TileEntityRedstoneTransmitter) this.world.getTileEntity(posBoundFrom)).activated))) {
            activated = true;
            world.getBlockState(pos).neighborChanged(world, pos, ModBlocks.block_transmitter_redstone, pos);
        } else {
            activated = false;
            world.getBlockState(pos).neighborChanged(world, pos, ModBlocks.block_transmitter_redstone, pos);

        }

        if ((posBound != null || posBound != pos) && posBound.getDistance(pos.getX(), pos.getY(), pos.getZ()) < 40) {
            if (world.getTileEntity(posBound) != null) {
                if (world.getTileEntity(posBound) instanceof TileEntityRedstoneTransmitter) {
                    TileEntityRedstoneTransmitter tile = (TileEntityRedstoneTransmitter) world.getTileEntity(posBound);
                    tile.posBoundFrom = pos;
                    tile.activated = requestActivation(activated);
                    world.getBlockState(posBound).neighborChanged(world, posBound, ModBlocks.block_transmitter_redstone, posBound);
                    world.getBlockState(pos).neighborChanged(world, pos, ModBlocks.block_transmitter_redstone, pos);
                }
            }
        }

    }//always prioritize commands of other blocks of the same kin, not the redstone (as in, always check if it's boundFrom

    public boolean requestActivation(boolean activated) {
        TileEntityRedstoneTransmitter tile = (TileEntityRedstoneTransmitter) world.getTileEntity(posBound);
        if (isBoundToATile(tile)) {
            if (world.isBlockPowered(tile.pos)) {
                return true;
            }
        } else if (isControlledByAnotherTile(tile)) {
            return activated;
        }
        return activated;
    }

    public boolean isBoundToATile(TileEntityRedstoneTransmitter tile) {
        if (tile.posBound != null && !tile.posBound.equals(tile.pos)) {
            if (world.getTileEntity(tile.posBound) instanceof TileEntityRedstoneTransmitter) {
                return true;
            }
        }
        return false;
    }

    public boolean isControlledByAnotherTile(TileEntityRedstoneTransmitter tile) {
        if (tile.posBoundFrom != null && !tile.posBoundFrom.equals(tile.pos)) {
            if (world.getTileEntity(tile.posBoundFrom) instanceof TileEntityRedstoneTransmitter) {
                return true;
            }
        }
        return false;
    }

    public boolean shouldProvideWeakPower() {
        if (activated && this.posBound != null && this.posBound.equals(pos)) {
            return true;
        }
        return false;
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
