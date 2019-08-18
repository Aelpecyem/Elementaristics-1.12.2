package de.aelpecyem.elementaristics.blocks.tileentity;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.networking.PacketHandler;
import de.aelpecyem.elementaristics.networking.tileentity.inventory.PacketUpdateInventory;
import de.aelpecyem.elementaristics.networking.tileentity.tick.PacketUpdateTickTime;
import de.aelpecyem.elementaristics.particles.ParticleGeneric;
import de.aelpecyem.elementaristics.recipe.PurifierRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;


public class TileEntityPurifier extends TileEntity implements ITickable, IHasTickCount, IHasInventory {


    public ItemStackHandler inventory = new ItemStackHandler(1) {
        @Override
        public int getSlotLimit(int slot) {
            return 3;
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
            PacketHandler.sendToAllAround(world, pos, 64, new PacketUpdateInventory(this, inventory));
            PacketHandler.sendToAllAround(world, pos, 64, new PacketUpdateTickTime(this, tickCount));
        }
        if (!(PurifierRecipes.getRecipeForInput(inventory.getStackInSlot(0)) == null)) {
            if (inventory.getStackInSlot(0).getCount() == PurifierRecipes.getRecipeForInput(inventory.getStackInSlot(0)).itemCount) {
                tickCount++;
                if (world.isRemote)
                doParticleShow();
            if (tickCount < 0) {
                tickCount = 0;
            }

            if (tickCount >= PurifierRecipes.getRecipeForInput(inventory.getStackInSlot(0)).time) {
                inventory.setStackInSlot(0, PurifierRecipes.getRecipeForInput(inventory.getStackInSlot(0)).output);
                tickCount = 0;
            }
        } else{
                tickCount = 0;
            }
        } else {
            tickCount = 0;
        }

    }

    private void doParticleShow() {
        if (tickCount % 5 == 0) {
            ParticleGeneric p = new ParticleGeneric(world, pos.getX() + 1F + (world.rand.nextGaussian() / 15), pos.getY() + 0.62F + (world.rand.nextGaussian() / 15), pos.getZ() + 1F + (world.rand.nextGaussian() / 15), 0, 0, 0, 14247, 1 + ((float) world.rand.nextGaussian() / 3), 60, 0, true, false, true, true, pos.getX() + 0.5F, pos.getY() + 0.56F, pos.getZ() + 0.5F);
            ParticleGeneric p2 = new ParticleGeneric(world, pos.getX() + (world.rand.nextGaussian() / 15), pos.getY() + 0.62F + (world.rand.nextGaussian() / 15), pos.getZ() + 1F + (world.rand.nextGaussian() / 15), 0, 0, 0, 14247, 1 + ((float) world.rand.nextGaussian() / 3), 60, 0, true, false, true, true, pos.getX() + 0.5F, pos.getY() + 0.56F, pos.getZ() + 0.5F);
            ParticleGeneric p3 = new ParticleGeneric(world, pos.getX() + 1F + (world.rand.nextGaussian() / 15), pos.getY() + 0.62F + (world.rand.nextGaussian() / 15), pos.getZ() + (world.rand.nextGaussian() / 15), 0, 0, 0, 14247, 1 + ((float) world.rand.nextGaussian() / 3), 60, 0, true, false, true, true, pos.getX() + 0.5F, pos.getY() + 0.56F, pos.getZ() + 0.5F);
            ParticleGeneric p4 = new ParticleGeneric(world, pos.getX() + (world.rand.nextGaussian() / 15), pos.getY() + 0.62F + (world.rand.nextGaussian() / 15), pos.getZ() + (world.rand.nextGaussian() / 15), 0, 0, 0, 14247, 1 + ((float) world.rand.nextGaussian() / 3), 60, 0, true, false, true, true, pos.getX() + 0.5F, pos.getY() + 0.56F, pos.getZ() + 0.5F);

            p.setAlphaF(1);
            p2.setAlphaF(1);
            p3.setAlphaF(1);
            p4.setAlphaF(1);

            Elementaristics.proxy.generateGenericParticles(p);
            Elementaristics.proxy.generateGenericParticles(p2);
            Elementaristics.proxy.generateGenericParticles(p3);
            Elementaristics.proxy.generateGenericParticles(p4);
        }
    }

    @Override
    public int getTickCount() {
        return tickCount;
    }

    @Override
    public void setTickCount(int tickCount) {
        this.tickCount = tickCount;
    }

    @Override
    public ItemStackHandler getInventory() {
        return inventory;
    }
}
