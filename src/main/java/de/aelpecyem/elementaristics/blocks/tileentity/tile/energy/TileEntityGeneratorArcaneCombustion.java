package de.aelpecyem.elementaristics.blocks.tileentity.tile.energy;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.init.ModItems;
import de.aelpecyem.elementaristics.items.base.ItemEssence;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.particles.ParticleGeneric;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityGeneratorArcaneCombustion extends TileEntityEnergy implements ITickable {


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
    public boolean canProvide() {
        return true;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("inventory", inventory.serializeNBT());
        compound.setInteger("tickCount", tickCount);
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
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
        super.update();
        if (tickCount <= 0) {
            if (this.storage.getEnergyStored() + 1 < storage.getMaxEnergyStored() && inventory.getStackInSlot(0).isItemEqual(new ItemStack(ModItems.essence, 1, Aspects.fire.getId()))) {
                inventory.getStackInSlot(0).shrink(1);
                tickCount += 100;
            }
        } else {
            if (storage.getEnergyStored() < storage.getMaxEnergyStored()) {
                this.storage.receiveEnergy(10, false);
                if (world.isRemote)
                    doParticles();
                tickCount--;
            }
        }
    }

    @SideOnly(Side.CLIENT)
    private void doParticles() {
        Elementaristics.proxy.generateGenericParticles(new ParticleGeneric(world, pos.getX() + 0.5 + (world.rand.nextGaussian() / 8F), pos.getY() + 0.3F, pos.getZ() + 0.5 + (world.rand.nextGaussian() / 8F), 0, 0.05F + world.rand.nextGaussian() / 1000F, 0F, Aspects.fire.getColor(), 3, 20 + world.rand.nextInt(4), 0, true, false, 0.95F, true));
    }
}
