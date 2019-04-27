package de.aelpecyem.elementaristics.blocks.tileentity;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.init.ModItems;
import de.aelpecyem.elementaristics.items.base.ItemEssence;
import de.aelpecyem.elementaristics.items.base.artifacts.rites.ItemAspects;
import de.aelpecyem.elementaristics.misc.elements.Aspect;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import de.aelpecyem.elementaristics.networking.PacketHandler;
import de.aelpecyem.elementaristics.networking.tileentity.basin.PacketUpdateBasin;
import de.aelpecyem.elementaristics.networking.tileentity.concentrator.PacketUpdateConcentrator;
import de.aelpecyem.elementaristics.particles.ParticleGeneric;
import de.aelpecyem.elementaristics.recipe.ConcentratorRecipes;
import de.aelpecyem.elementaristics.recipe.InfusionRecipes;
import de.aelpecyem.elementaristics.recipe.base.InfusionRecipe;
import de.aelpecyem.elementaristics.util.MiscUtil;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.apache.commons.lang3.ArrayUtils;
import scala.Int;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.*;
import java.util.List;

public class TileEntityInfusionBasin extends TileEntity implements ITickable {

    public ItemStackHandler inventory = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            if (inventory.getStackInSlot(0).getItem() instanceof ItemEssence) {
                EntityItem item = new EntityItem(world, pos.getX(), pos.getY() + 1.5, pos.getZ(), inventory.getStackInSlot(0));
                world.spawnEntity(item);
                inventory.setStackInSlot(slot, ItemStack.EMPTY);
            }

        }

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }

    };
    public int tickCount;
    public List<Integer> aspectIDs = new ArrayList<>();
    public int aspectCount;
    public int fillCount;
    public long lastChangeTime;

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("inventory", inventory.serializeNBT());
        compound.setInteger("tickCount", tickCount);
        for (int i = 0; i < aspectIDs.size(); i++) {
            compound.setInteger("aspect" + i, aspectIDs.get(i));
        }
        aspectCount = aspectIDs.size();
        compound.setInteger("aspectCount", aspectCount);
        compound.setInteger("fillCount", fillCount);
        compound.setLong("lastChangeTime", lastChangeTime);
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        inventory.deserializeNBT(compound.getCompoundTag("inventory"));
        tickCount = compound.getInteger("tickCount");
        aspectCount = compound.getInteger("aspectCount");
        for (int i = 0; i < aspectCount; i++) {
            aspectIDs.add(compound.getInteger("aspect" + i));
        }
        fillCount = compound.getInteger("fillCount");
        lastChangeTime = compound.getLong("lastChangeTime");
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
        doPassiveParticleShow();
        if (!world.isRemote) {
            lastChangeTime = world.getTotalWorldTime();
            PacketHandler.sendToAllAround(world, pos, 64, new PacketUpdateBasin(TileEntityInfusionBasin.this));
        }
        if (!inventory.getStackInSlot(0).isEmpty() && fillCount > 0 && aspectIDs.size() > 0) {
            tickCount++;
            doParticleShow();
            if (tickCount > 100) {
                if (InfusionRecipes.getRecipeForInput(inventory.getStackInSlot(0)) != null) {
                    if (aspectIDs != null && getAspectSet().containsAll(InfusionRecipes.getRecipeForInput(inventory.getStackInSlot(0)).aspects)) {
                        for (Aspect aspect : InfusionRecipes.getRecipeForInput(inventory.getStackInSlot(0)).aspects) {
                            aspectIDs.remove((Integer) aspect.getId());
                        }
                        if (!world.isRemote)
                            world.spawnEntity(new EntityItem(world, pos.getX() + 0.5, pos.getY() + 1.2, pos.getZ() + 0.5, InfusionRecipes.getRecipeForInput(inventory.getStackInSlot(0)).output));
                        inventory.setStackInSlot(0, ItemStack.EMPTY);
                        tickCount = 0;
                        fillCount--;
                    }
                } else {
                    aspectIDs.remove(world.rand.nextInt(aspectIDs.size()));
                    if (!world.isRemote)
                        world.spawnEntity(new EntityItem(world, pos.getX() + 0.5, pos.getY() + 1.2, pos.getZ() + 0.5, ModItems.maganized_matter.getDefaultInstance()));
                    inventory.setStackInSlot(0, ItemStack.EMPTY);
                    tickCount = 0;
                    fillCount--;
                }
            }
        }


    }

    public Set<Aspect> getAspectSet() {
        Set<Aspect> aspectSet = new HashSet<>();
        for (int i : aspectIDs) {
            aspectSet.add(Aspects.getElementById(i));
        }
        return aspectSet;
    }

    private void doParticleShow() {
        if (world.rand.nextInt(10) == 5 && !aspectIDs.isEmpty() && fillCount > 0) {
            Elementaristics.proxy.generateGenericParticles(world, pos.getX() + 0.5 + (world.rand.nextGaussian() / 10), pos.getY() + 0.7 + (world.rand.nextGaussian() / 12), pos.getZ() + 0.5 + (world.rand.nextGaussian() / 10), Aspects.magan.getColor(), 3, 100, 0, true, true);
        }
    }

    private void doPassiveParticleShow() {
        if (world.rand.nextInt(10) == 5 && !aspectIDs.isEmpty() && fillCount > 0) {
            Elementaristics.proxy.generateGenericParticles(world, pos.getX() + 0.5 + (world.rand.nextGaussian() / 10), pos.getY() + 0.7 + (world.rand.nextGaussian() / 12), pos.getZ() + 0.5 + (world.rand.nextGaussian() / 10), MiscUtil.coverColorToInt(MiscUtil.blend(MiscUtil.getColorForEssenceIds(aspectIDs), new Color(47, 130, 232, 205), Math.min(0.1 * aspectIDs.size(), 0.9), 1 - Math.min(0.1 * aspectIDs.size(), 0.9))), 3, 100, 0, true, true);
        }
    }

    public boolean addAspects(ItemStack stack) {
        if (stack.getItem() instanceof ItemEssence) {
            aspectIDs.add(stack.getMetadata());
            return true;
        }
        return false;
    }


}
