package de.aelpecyem.elementaristics.blocks.tileentity.energy;

import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityInfusionBasin;
import de.aelpecyem.elementaristics.capability.energy.EnergyCapability;
import de.aelpecyem.elementaristics.init.ModItems;
import de.aelpecyem.elementaristics.items.base.ItemEssence;
import de.aelpecyem.elementaristics.items.base.artifacts.rites.ItemAspects;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.networking.PacketHandler;
import de.aelpecyem.elementaristics.networking.tileentity.basin.PacketUpdateBasin;
import de.aelpecyem.elementaristics.networking.tileentity.energy.generatorCombustion.PacketUpdateCombustionGenerator;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityGeneratorArcaneCombustion extends TileEntity implements ITickable {

    public EnergyCapability storage = new EnergyCapability(1000, 10);

    public ItemStackHandler inventory = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            if (!inventory.getStackInSlot(0).isEmpty()) {
                if (!(inventory.getStackInSlot(0).getItem() instanceof ItemEssence && inventory.getStackInSlot(0).getMetadata() == Aspects.fire.getId())) {
                    EntityItem item = new EntityItem(world, pos.getX(), pos.getY() + 1.5, pos.getZ(), inventory.getStackInSlot(0));
                    world.spawnEntity(item);
                    inventory.setStackInSlot(slot, ItemStack.EMPTY);
                }
                super.onContentsChanged(slot);
            }
        }

        @Override
        public int getSlotLimit(int slot) {
            return 64;
        }
    };
    public int tickCount;

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        storage.writeToNBT(compound);
        compound.setTag("inventory", inventory.serializeNBT());
        compound.setInteger("tickCount", tickCount);
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        storage.readFromNBT(compound);
        inventory.deserializeNBT(compound.getCompoundTag("inventory"));
        tickCount = compound.getInteger("tickCount");
        super.readFromNBT(compound);
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(getPos(), getPos().add(1, 2, 1));
    }


    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || capability == CapabilityEnergy.ENERGY || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? (T) inventory : capability == CapabilityEnergy.ENERGY ? (T) storage : super.getCapability(capability, facing);
    }


    @Override
    public void update() {
        System.out.println(tickCount);
        if (!world.isRemote) {
            PacketHandler.sendToAllAround(world, pos, 64, new PacketUpdateCombustionGenerator(TileEntityGeneratorArcaneCombustion.this));
        }
        if (tickCount <= 0) {
            if (this.storage.canReceive() && inventory.getStackInSlot(0).isItemEqual(new ItemStack(ModItems.essence, 1, Aspects.fire.getId()))) {
                inventory.getStackInSlot(0).shrink(1);
                tickCount += 400;
            }
        } else {
            this.storage.receiveEnergy(10, false);
            tickCount--;
        }
        System.out.println("Energy: " + storage.getEnergyStored());
    }


}
