package de.aelpecyem.elementaristics.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;


public class PlayerCapProvider implements ICapabilitySerializable<NBTBase> {


    @CapabilityInject(IPlayerCapabilities.class)
    public static final Capability<IPlayerCapabilities> ELEMENTARISTICS_CAP = null;
    private IPlayerCapabilities instance = ELEMENTARISTICS_CAP.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == ELEMENTARISTICS_CAP;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return capability == ELEMENTARISTICS_CAP ? ELEMENTARISTICS_CAP.<T>cast(this.instance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return ELEMENTARISTICS_CAP.getStorage().writeNBT(ELEMENTARISTICS_CAP, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        ELEMENTARISTICS_CAP.getStorage().readNBT(ELEMENTARISTICS_CAP, this.instance, null, nbt);
    }
}
