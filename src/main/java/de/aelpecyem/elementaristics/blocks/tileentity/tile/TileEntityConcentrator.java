package de.aelpecyem.elementaristics.blocks.tileentity.tile;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.init.ModItems;
import de.aelpecyem.elementaristics.items.base.ItemEssence;
import de.aelpecyem.elementaristics.items.base.artifacts.rites.ItemAspects;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.networking.PacketHandler;
import de.aelpecyem.elementaristics.particles.ParticleGeneric;
import de.aelpecyem.elementaristics.recipe.ConcentratorRecipes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;


public class TileEntityConcentrator extends TileEntity implements ITickable {

    public ItemStackHandler inventory = new ItemStackHandler(2) {

        @Override
        protected void onContentsChanged(int slot) {
            if (!inventory.getStackInSlot(1).isEmpty()) {
                if (!(inventory.getStackInSlot(1).getItem() instanceof ItemEssence || inventory.getStackInSlot(1).getItem() instanceof ItemAspects)) {
                    EntityItem item = new EntityItem(world, pos.getX(), pos.getY() + 1.5, pos.getZ(), inventory.getStackInSlot(slot));
                    world.spawnEntity(item);
                    inventory.setStackInSlot(slot, ItemStack.EMPTY);
                    markDirty();
                }
            }

        }

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }

    };
    public int tickCount;

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
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? (T) inventory : super.getCapability(capability, facing);
    }


    @Override
    public void update() {
        if (!world.isRemote) {
            PacketHandler.syncTile(this);
        }
        if (!inventory.getStackInSlot(0).isEmpty() && !inventory.getStackInSlot(1).isEmpty()) {
            tickCount++;
            if (world.isRemote)
                doParticleShow();

            if (tickCount >= 300) {
                if (ConcentratorRecipes.getRecipeForInput(inventory.getStackInSlot(0), inventory.getStackInSlot(1)) != null) {
                    inventory.setStackInSlot(0, ConcentratorRecipes.getRecipeForInput(inventory.getStackInSlot(0), inventory.getStackInSlot(1)).output);
                } else {
                    inventory.setStackInSlot(0, new ItemStack(ModItems.chaotic_matter));
                    inventory.setStackInSlot(1, ItemStack.EMPTY);
                }
                tickCount = 0;
            }
        } else {
            tickCount = 0;
        }
    }

    private void doParticleShow() {
        if (inventory.getStackInSlot(1).getItem() instanceof ItemEssence)
            Elementaristics.proxy.generateGenericParticles(new ParticleGeneric(world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, world.rand.nextGaussian() * 0.02F, Math.abs(world.rand.nextGaussian()) * 0.06F, world.rand.nextGaussian() * 0.02F, Aspects.getElementById(inventory.getStackInSlot(1).getMetadata()).getColor(), 2, 10, 0, true, false, 0.5F, true));
        else if (inventory.getStackInSlot(1).getItem() instanceof ItemAspects)
            Elementaristics.proxy.generateGenericParticles(new ParticleGeneric(world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, world.rand.nextGaussian() * 0.02F, Math.abs(world.rand.nextGaussian()) * 0.06F, world.rand.nextGaussian() * 0.02F, ((ItemAspects) inventory.getStackInSlot(1).getItem()).getAspects().get(0).getColor(), 2, 10, 0, true, false, 0.5F, true));

    }
}
