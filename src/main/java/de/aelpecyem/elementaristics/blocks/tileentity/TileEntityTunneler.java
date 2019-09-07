package de.aelpecyem.elementaristics.blocks.tileentity;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.init.ModItems;
import de.aelpecyem.elementaristics.networking.PacketHandler;
import de.aelpecyem.elementaristics.networking.tileentity.inventory.PacketUpdateInventory;
import de.aelpecyem.elementaristics.networking.tileentity.tick.PacketUpdateTickTime;
import de.aelpecyem.elementaristics.particles.ParticleGeneric;
import de.aelpecyem.elementaristics.recipe.TunnelerRecipes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TileEntityTunneler extends TileEntity implements ITickable, IHasTickCount, IHasInventory {


    public ItemStackHandler inventory = new ItemStackHandler(2) {
        @Override
        public int getSlotLimit(int slot) {
            if (slot == 0) {
                return 1;
            } else {
                return 16;
            }
        }

        @Override
        protected void onContentsChanged(int slot) {
            if (!inventory.getStackInSlot(1).isEmpty()) {
                if (!(inventory.getStackInSlot(1).getItem() == ModItems.matter_accelerating_module)) {
                    EntityItem item = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), inventory.getStackInSlot(1));
                    world.spawnEntity(item);
                    inventory.setStackInSlot(slot, ItemStack.EMPTY);
                }
            }

        }
    };
    public int tickCount;
    private boolean crafting;

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("inventory", inventory.serializeNBT());
        compound.setInteger("tickCount", tickCount);
        compound.setBoolean("crafting", crafting);
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        inventory.deserializeNBT(compound.getCompoundTag("inventory"));
        tickCount = compound.getInteger("tickCount");
        crafting = compound.getBoolean("crafting");
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
            PacketHandler.sendToAllAround(world, pos, 64, new PacketUpdateInventory(TileEntityTunneler.this, inventory));
            PacketHandler.sendToAllAround(world, pos, 64, new PacketUpdateTickTime(TileEntityTunneler.this, tickCount));

        }
        if (world.isBlockPowered(pos)) {
            if (!inventory.getStackInSlot(0).isEmpty() && inventory.getStackInSlot(1).isItemEqual(new ItemStack(ModItems.matter_accelerating_module))) {
                if (checkSurroundings()) {
                    tickCount++;
                    if (world.isRemote)
                        doParticleShow();
                    if (tickCount >= 200) {
                        if (TunnelerRecipes.getRecipeForInput(inventory.getStackInSlot(0)) != null) {
                            List<ItemStack> missingItems = new ArrayList<>();
                            missingItems.addAll(TunnelerRecipes.getRecipeForInput(inventory.getStackInSlot(0)).filterItems);
                            for (int i = -1; i > -4; i--) {
                                if (world.getTileEntity(new BlockPos(pos.getX(), pos.getY() + i, pos.getZ())) instanceof TileEntityFilterHolder) {
                                    TileEntityFilterHolder filter = (TileEntityFilterHolder) world.getTileEntity(new BlockPos(pos.getX(), pos.getY() + i, pos.getZ()));
                                    for (Iterator<ItemStack> iterator = missingItems.iterator(); iterator.hasNext();) {
                                        ItemStack stack = iterator.next();
                                        if(stack.isItemEqual(filter.inventory.getStackInSlot(0))) {
                                            filter.inventory.setStackInSlot(0, ItemStack.EMPTY);
                                            iterator.remove();
                                        }
                                    }
                                }
                            }
                            if (missingItems.isEmpty()) {
                                TileEntityPedestal pedestal = (TileEntityPedestal) world.getTileEntity(new BlockPos(pos.getX(), pos.getY() - 4, pos.getZ()));
                                if (pedestal.inventory.getStackInSlot(0).isEmpty()) {
                                    pedestal.inventory.setStackInSlot(0, TunnelerRecipes.getRecipeForInput(inventory.getStackInSlot(0)).output);
                                }
                            } else {
                                TileEntityPedestal pedestal = (TileEntityPedestal) world.getTileEntity(new BlockPos(pos.getX(), pos.getY() - 4, pos.getZ()));
                                if (pedestal.inventory.getStackInSlot(0).isEmpty()) {
                                    pedestal.inventory.setStackInSlot(0, new ItemStack(ModItems.maganized_matter));
                                }
                            }
                        } else {
                            TileEntityPedestal pedestal = (TileEntityPedestal) world.getTileEntity(new BlockPos(pos.getX(), pos.getY() - 4, pos.getZ()));
                            if (pedestal.inventory.getStackInSlot(0).isEmpty()) {
                                pedestal.inventory.setStackInSlot(0, new ItemStack(ModItems.chaotic_matter));
                            }
                        }
                        inventory.setStackInSlot(0, ItemStack.EMPTY);
                        inventory.setStackInSlot(1, new ItemStack(inventory.getStackInSlot(1).getItem(), inventory.getStackInSlot(1).getCount() - 1));
                        tickCount = 0;
                    }
                } else {
                    tickCount = 0;
                }
            }

            if (tickCount < 0) {
                tickCount = 0;
            }


        }
    }

    public boolean checkSurroundings() {
        TileEntity tileFilter1 = world.getTileEntity(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ()));
        TileEntity tileFilter2 = world.getTileEntity(new BlockPos(pos.getX(), pos.getY() - 2, pos.getZ()));
        TileEntity tileFilter3 = world.getTileEntity(new BlockPos(pos.getX(), pos.getY() - 3, pos.getZ()));

        TileEntity tilePedestal = world.getTileEntity(new BlockPos(pos.getX(), pos.getY() - 4, pos.getZ()));

        if (tileFilter1 instanceof TileEntityFilterHolder && tileFilter2 instanceof TileEntityFilterHolder && tileFilter3 instanceof TileEntityFilterHolder && tilePedestal instanceof TileEntityPedestal) {
            TileEntityPedestal pedestal = (TileEntityPedestal) tilePedestal;
            TileEntityFilterHolder filter1 = (TileEntityFilterHolder) tileFilter1;
            TileEntityFilterHolder filter2 = (TileEntityFilterHolder) tileFilter2;
            TileEntityFilterHolder filter3 = (TileEntityFilterHolder) tileFilter3;
            if (!filter1.inventory.getStackInSlot(0).isEmpty() && !filter2.inventory.getStackInSlot(0).isEmpty() && !filter3.inventory.getStackInSlot(0).isEmpty() && pedestal.inventory.getStackInSlot(0).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private void doParticleShow() {
        Elementaristics.proxy.generateGenericParticles(new ParticleGeneric(world, pos.getX() + 0.5, pos.getY() - 0.1F, pos.getZ() + 0.5, 0, -0.1, 0, 48895, 3, 40, 0, true, false, 0.9F, false));
        Elementaristics.proxy.generateGenericParticles(new ParticleGeneric(world, pos.getX() + 0.5, pos.getY() - 0.1F, pos.getZ() + 0.5, 0, -0.1F, 0, 48895, 3, 40, 0, true, false, 0.9F, false));
        Elementaristics.proxy.generateGenericParticles(new ParticleGeneric(world, pos.getX() + 0.5, pos.getY() - 0.1F, pos.getZ() + 0.5, 0, -0.1F, 0, 48895, 3, 40, 0, true, false, 0.9F, false));

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