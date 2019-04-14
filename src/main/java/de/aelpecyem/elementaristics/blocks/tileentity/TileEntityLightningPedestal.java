package de.aelpecyem.elementaristics.blocks.tileentity;


import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.networking.PacketHandler;
import de.aelpecyem.elementaristics.networking.tileentity.pedestallightning.PacketUpdateLightningPedestal;
import de.aelpecyem.elementaristics.particles.ParticleGeneric;
import de.aelpecyem.elementaristics.recipe.PedestalRecipes;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Random;

public class TileEntityLightningPedestal extends TileEntity implements ITickable {

    public EnumParticleTypes particle = EnumParticleTypes.CRIT_MAGIC;
    public ItemStackHandler inventory = new ItemStackHandler(1) {


        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }
    };
    public int tickCount;
    public long lastChangeTime;
    public ItemStack stackCrafting = ItemStack.EMPTY;
    Random random = new Random();
    private boolean crafting;

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("inventory", inventory.serializeNBT());
        compound.setInteger("tickCount", tickCount);
        compound.setLong("lastChangeTime", lastChangeTime);
        compound.setBoolean("crafting", crafting);
        compound.setTag("stackCrafting", stackCrafting.serializeNBT());
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        inventory.deserializeNBT(compound.getCompoundTag("inventory"));
        tickCount = compound.getInteger("tickCount");
        lastChangeTime = compound.getInteger("lastChangeTime");
        crafting = compound.getBoolean("crafting");
        stackCrafting.deserializeNBT(compound.getCompoundTag("stackCrafting"));
        super.readFromNBT(compound);
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(getPos(), getPos().add(1, 2, 1));
    }

    public boolean isCrafting() {
        return crafting;
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
            lastChangeTime = world.getTotalWorldTime();
            PacketHandler.sendToAllAround(world, pos, 64, new PacketUpdateLightningPedestal(TileEntityLightningPedestal.this));
        }
        if (!(PedestalRecipes.getRecipeForInput(inventory.getStackInSlot(0)) == null)) {
            tickCount++;
            if (tickCount < 0) {
                tickCount = 0;
            }
            if (tickCount == 60) {
                world.spawnEntity(new EntityLightningBolt(world, getPos().getX(), getPos().getY() + 1, getPos().getZ(), true));
            }
            if (tickCount == 80) {
                world.spawnEntity(new EntityLightningBolt(world, getPos().getX(), getPos().getY() + 1, getPos().getZ(), true));
            }
            if (tickCount == 100) {
                world.spawnEntity(new EntityLightningBolt(world, getPos().getX(), getPos().getY() + 1, getPos().getZ(), true));
            }

            if (tickCount >= 120) {
                doParticleShow(getWorld());
            }

            if (tickCount >= 240) {
                inventory.setStackInSlot(0, PedestalRecipes.getRecipeForInput(inventory.getStackInSlot(0)).output);
                tickCount = 0;
            }
        }else{
            tickCount = 0;
        }

    }

    private void doParticleShow(World world) {
        Elementaristics.proxy.generateGenericParticles(new ParticleGeneric(world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, world.rand.nextGaussian() * 0.125F, Math.abs(world.rand.nextGaussian()) * 0.125F, world.rand.nextGaussian() * 0.125F, 14958080, 3, 10, 0, true, false, 1F));
        Elementaristics.proxy.generateGenericParticles(new ParticleGeneric(world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, world.rand.nextGaussian() * 0.1F, Math.abs(world.rand.nextGaussian()) * 0.1F, world.rand.nextGaussian() * 0.1F, 14777600, 1, 10, 0, true, false, 1F));
        Elementaristics.proxy.generateGenericParticles(new ParticleGeneric(world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, world.rand.nextGaussian() * 0.075F, Math.abs(world.rand.nextGaussian()) * 0.075F, world.rand.nextGaussian() * 0.075F, 15057664, 1, 10, 0, true, false, 1F));
    }
}
