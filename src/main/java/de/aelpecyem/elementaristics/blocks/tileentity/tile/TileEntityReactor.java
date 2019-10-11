package de.aelpecyem.elementaristics.blocks.tileentity.tile;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.blocks.tileentity.blocks.BlockReactor;
import de.aelpecyem.elementaristics.init.ModItems;
import de.aelpecyem.elementaristics.networking.PacketHandler;
import de.aelpecyem.elementaristics.particles.ParticleGeneric;
import de.aelpecyem.elementaristics.recipe.ReactorRecipes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Random;

public class TileEntityReactor extends TileEntity implements ITickable {

    public ItemStackHandler inventory = new ItemStackHandler(2) {


        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }
    };
    public int tickCount;
    Random random = new Random();

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
        if (tickCount >= 1) {
            tickCount++;
            if (world.isRemote)
                doParticleShow();

            if (tickCount < 0) {
                tickCount = 0;
            }
            if (tickCount >= 60) {
                if (ReactorRecipes.getRecipeForInputs(inventory.getStackInSlot(0), inventory.getStackInSlot(1)) != null) {
                    EntityItem item = new EntityItem(world, pos.getX() + 0.5F, pos.getY() + 1.2F, pos.getZ() + 0.5, ReactorRecipes.getRecipeForInputs(inventory.getStackInSlot(0), inventory.getStackInSlot(1)).output);
                    if (!world.isRemote)
                        world.spawnEntity(item);
                    inventory.setStackInSlot(0, ItemStack.EMPTY);
                    inventory.setStackInSlot(1, ItemStack.EMPTY);
                } else if (ReactorRecipes.getRecipeForInputs(inventory.getStackInSlot(1), inventory.getStackInSlot(0)) != null) {
                    EntityItem item = new EntityItem(world, pos.getX() + 0.5F, pos.getY() + 1.2F, pos.getZ() + 0.5, ReactorRecipes.getRecipeForInputs(inventory.getStackInSlot(1), inventory.getStackInSlot(0)).output);
                    if (!world.isRemote)
                        world.spawnEntity(item);
                    inventory.setStackInSlot(0, ItemStack.EMPTY);
                    inventory.setStackInSlot(1, ItemStack.EMPTY);
                } else {
                    inventory.setStackInSlot(0, ItemStack.EMPTY);
                    inventory.setStackInSlot(1, ItemStack.EMPTY);
                    EntityItem item = new EntityItem(world, pos.getX() + 0.5F, pos.getY() + 1.2F, pos.getZ() + 0.5, new ItemStack(ModItems.ash));
                    if (!world.isRemote)
                        world.spawnEntity(item);
                }
                tickCount = 0;
            }
        } else {
            tickCount = 0;
        }


    }

    public EnumFacing getBlockFacing() {
        return this.world.getBlockState(this.pos).getValue(BlockReactor.FACING);
    }

    @SideOnly(Side.CLIENT)
    private void doParticleShow() {
        Elementaristics.proxy.generateGenericParticles(new ParticleGeneric(world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, world.rand.nextGaussian() * 0.1F, Math.abs(world.rand.nextGaussian()) * 0.1F, world.rand.nextGaussian() * 0.1F, 14958080, 3, 10, 0, false, false, 0.99F, true));
        Elementaristics.proxy.generateGenericParticles(new ParticleGeneric(world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, world.rand.nextGaussian() * 0.08F, Math.abs(world.rand.nextGaussian()) * 0.08F, world.rand.nextGaussian() * 0.08F, 14777600, 1, 10, 0, false, false, 0.99F, true));
        Elementaristics.proxy.generateGenericParticles(new ParticleGeneric(world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, world.rand.nextGaussian() * 0.06F, Math.abs(world.rand.nextGaussian()) * 0.06F, world.rand.nextGaussian() * 0.06F, 15057664, 1, 10, 0, false, false, 0.99F, true));

    }

}



