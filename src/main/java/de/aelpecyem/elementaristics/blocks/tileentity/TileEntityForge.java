package de.aelpecyem.elementaristics.blocks.tileentity;

import com.sun.istack.internal.Nullable;
import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.blocks.tileentity.blocks.BlockReactor;
import de.aelpecyem.elementaristics.init.ModItems;
import de.aelpecyem.elementaristics.items.base.thaumagral.*;
import de.aelpecyem.elementaristics.networking.PacketHandler;
import de.aelpecyem.elementaristics.networking.forge.PacketUpdateForge;
import de.aelpecyem.elementaristics.particles.ParticleGeneric;
import de.aelpecyem.elementaristics.recipe.ForgeRecipes;
import de.aelpecyem.elementaristics.recipe.base.ForgeRecipe;
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

import java.util.Random;

public class TileEntityForge extends TileEntity implements ITickable {
    public ItemStackHandler inventory = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
         /*   if (!inventory.getStackInSlot(0).isEmpty()) {
                if (!(inventory.getStackInSlot(0).getItem() instanceof ItemPartBlade || inventory.getStackInSlot(0).getItem() instanceof ItemThaumagral)) {
                    EntityItem item = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), inventory.getStackInSlot(0));
                    world.spawnEntity(item);
                    inventory.setStackInSlot(slot, ItemStack.EMPTY);
                }
            }
            if (!inventory.getStackInSlot(1).isEmpty()) {
                if (!(inventory.getStackInSlot(1).getItem() instanceof ItemPartHandle)) {
                    EntityItem item = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), inventory.getStackInSlot(1));
                    world.spawnEntity(item);
                    inventory.setStackInSlot(slot, ItemStack.EMPTY);
                }
            }
            if (!inventory.getStackInSlot(2).isEmpty()) {
                if (!(inventory.getStackInSlot(2).getItem() instanceof ItemPartCore)) {
                    EntityItem item = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), inventory.getStackInSlot(2));
                    world.spawnEntity(item);
                    inventory.setStackInSlot(slot, ItemStack.EMPTY);
                }
            }
            if (!inventory.getStackInSlot(3).isEmpty()) {
                if (!(inventory.getStackInSlot(3).getItem() instanceof ItemPartEngravings)) {
                    EntityItem item = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), inventory.getStackInSlot(3));
                    world.spawnEntity(item);
                    inventory.setStackInSlot(slot, ItemStack.EMPTY);
                }
            }*/

        }

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }
    };
    public int hitCount;
    public long lastChangeTime;
    Random random = new Random();

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("inventory", inventory.serializeNBT());
        compound.setInteger("hitCount", hitCount);
        compound.setLong("lastChangeTime", lastChangeTime);
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        inventory.deserializeNBT(compound.getCompoundTag("inventory"));
        hitCount = compound.getInteger("hitCount");
        lastChangeTime = compound.getInteger("lastChangeTime");
        super.readFromNBT(compound);
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(getPos(), getPos().add(1, 2, 1));
    }


    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? (T) inventory : super.getCapability(capability, facing);
    }

    @Override
    public void update() {
        if (!world.isRemote) {
            lastChangeTime = world.getTotalWorldTime();
            PacketHandler.sendToAllAround(world, pos, 64, new PacketUpdateForge(TileEntityForge.this));
        }
        if (ForgeRecipes.getRecipeForInput(inventory.getStackInSlot(0)) != null){
            if (hitCount > ForgeRecipes.getRecipeForInput(inventory.getStackInSlot(0)).hitAmount){
                inventory.setStackInSlot(1, ItemStack.EMPTY);
                inventory.setStackInSlot(0, ForgeRecipes.getRecipeForInput(inventory.getStackInSlot(0)).output);
                hitCount = 0;
            }
        }else{
            hitCount = 0;
        }
    }

    public EnumFacing getBlockFacing() {
        return this.world.getBlockState(this.pos).getValue(BlockReactor.FACING);
    }

    public void doParticleShow() {
        Elementaristics.proxy.generateGenericParticles(new ParticleGeneric(world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, world.rand.nextGaussian() * 0.05F, Math.abs(world.rand.nextGaussian()) * 0.1F, world.rand.nextGaussian() * 0.05F, 14958080, 3, 10, 0, true, false, 1F));
        Elementaristics.proxy.generateGenericParticles(new ParticleGeneric(world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, world.rand.nextGaussian() * 0.04F, Math.abs(world.rand.nextGaussian()) * 0.08F, world.rand.nextGaussian() * 0.04F, 14777600, 1, 10, 0, true, false, 1F));
        Elementaristics.proxy.generateGenericParticles(new ParticleGeneric(world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, world.rand.nextGaussian() * 0.03F, Math.abs(world.rand.nextGaussian()) * 0.06F, world.rand.nextGaussian() * 0.03F, 15057664, 1, 10, 0, true, false, 1F));
    }

}
