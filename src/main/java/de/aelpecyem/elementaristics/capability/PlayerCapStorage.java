package de.aelpecyem.elementaristics.capability;

import de.aelpecyem.elementaristics.entity.EntityCultist;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;


public class PlayerCapStorage implements Capability.IStorage<IPlayerCapabilities> {

    @Override
    public NBTBase writeNBT(Capability<IPlayerCapabilities> capability, IPlayerCapabilities instance, EnumFacing side) {
        final NBTTagCompound compound = new NBTTagCompound();

        compound.setInteger("soulId", instance.getSoulId());
        compound.setBoolean("knowsSoul", instance.knowsSoul());
        compound.setFloat("currentMagan", instance.getMagan());
        compound.setFloat("maganRegenPerSecond", instance.getMaganRegenPerTick());
        compound.setFloat("maxMagan", instance.getMaxMagan());
        compound.setInteger("ascensionStage", instance.getPlayerAscensionStage());
        compound.setInteger("cultistCount", instance.getCultistCount());

        return compound;
    }


    @Override
    public void readNBT(Capability<IPlayerCapabilities> capability, IPlayerCapabilities instance, EnumFacing side, NBTBase nbt) {
        final NBTTagCompound compound = (NBTTagCompound) nbt;

        instance.setSoulId(compound.getInteger("soulId"));
        instance.setKnowsSoul(compound.getBoolean("knowsSoul"));
        instance.setMaxMagan(compound.getFloat("maxMagan"));
        instance.setMagan(compound.getFloat("currentMagan"));
        instance.setMaganRegenPerTick(compound.getFloat("maganRegenPerSecond"));
        instance.setPlayerAscensionStage(compound.getInteger("ascensionStage"));
        instance.setCultistCount(compound.getInteger("cultistCount"));

    }
}
