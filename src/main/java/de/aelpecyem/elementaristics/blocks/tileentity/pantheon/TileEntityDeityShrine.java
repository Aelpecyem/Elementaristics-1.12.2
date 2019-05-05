package de.aelpecyem.elementaristics.blocks.tileentity.pantheon;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityAltar;
import de.aelpecyem.elementaristics.blocks.tileentity.blocks.BlockReactor;
import de.aelpecyem.elementaristics.blocks.tileentity.energy.TileEntityGeneratorArcaneCombustion;
import de.aelpecyem.elementaristics.capability.energy.EnergyCapability;
import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import de.aelpecyem.elementaristics.capability.player.souls.Soul;
import de.aelpecyem.elementaristics.entity.EntityCultist;
import de.aelpecyem.elementaristics.init.ModItems;
import de.aelpecyem.elementaristics.init.RiteInit;
import de.aelpecyem.elementaristics.init.SoulInit;
import de.aelpecyem.elementaristics.items.base.ItemEssence;
import de.aelpecyem.elementaristics.items.base.artifacts.rites.IHasRiteUse;
import de.aelpecyem.elementaristics.misc.elements.Aspect;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.misc.pantheon.Deities;
import de.aelpecyem.elementaristics.misc.pantheon.Deity;
import de.aelpecyem.elementaristics.misc.rites.RiteBase;
import de.aelpecyem.elementaristics.networking.PacketHandler;
import de.aelpecyem.elementaristics.networking.tileentity.altar.PacketUpdateAltar;
import de.aelpecyem.elementaristics.networking.tileentity.energy.generatorCombustion.PacketUpdateCombustionGenerator;
import de.aelpecyem.elementaristics.particles.ParticleGeneric;
import de.aelpecyem.elementaristics.util.MaganUtil;
import de.aelpecyem.elementaristics.util.SoulUtil;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.*;

public class TileEntityDeityShrine extends TileEntity implements ITickable {

    public EnergyCapability storage = new EnergyCapability(1000, 1);
    public int tickCount;
    public String deityBound;
    public boolean isStatue;

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        storage.writeToNBT(compound);
        compound.setInteger("tickCount", tickCount);
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        storage.readFromNBT(compound);
        tickCount = compound.getInteger("tickCount");
        super.readFromNBT(compound);
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(getPos(), getPos().add(1, 2, 1));
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
        if (!world.isRemote) {
            //     PacketHandler.sendToAllAround(world, pos, 64, new PacketUpdateCombustionGenerator(TileEntityGeneratorArcaneCombustion.this));
        }
        Deity deityActive = Deities.deities.get(new ResourceLocation(deityBound));
        if (world.getWorldTime() >= deityActive.getTickTimeBegin() && world.getWorldTime() <= deityActive.getTickTimeEnd()) {
            if (isStatue) {
                deityActive.statueEffect(this);
            } else {
                deityActive.symbolEffect(this);
            }
        }
      /*  if (tickCount <= 0) {
            if (this.storage.canReceive() && inventory.getStackInSlot(0).isItemEqual(new ItemStack(ModItems.essence, 1, Aspects.fire.getId()))) {
                inventory.getStackInSlot(0).shrink(1);
                tickCount += 400;
            }
        } else {
            this.storage.receiveEnergy(1, false);
            tickCount--;
        }*/
        //   System.out.println("Energy: " + storage.getEnergyStored());
    }
}



