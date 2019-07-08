package de.aelpecyem.elementaristics.capability.chunk;

import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class ChunkCapStorage implements Capability.IStorage<IChunkCapabilities> {

    @Override
    public NBTBase writeNBT(Capability<IChunkCapabilities> capability, IChunkCapabilities instance, EnumFacing side) {
        final NBTTagCompound compound = new NBTTagCompound();
        compound.setInteger("influenceId", instance.getInfluenceId());
        return compound;
    }


    @Override
    public void readNBT(Capability<IChunkCapabilities> capability, IChunkCapabilities instance, EnumFacing side, NBTBase nbt) {
        final NBTTagCompound compound = (NBTTagCompound) nbt;
        instance.setInfluenceId(compound.getInteger("influenceId"));
    }
}
