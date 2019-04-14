package de.aelpecyem.elementaristics.blocks.tileentity;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.networking.PacketHandler;
import de.aelpecyem.elementaristics.networking.tileentity.purifier.PacketUpdatePurifier;
import de.aelpecyem.elementaristics.recipe.PurifierRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;


public class TileEntityPurifier extends TileEntity implements ITickable {


    public EnumParticleTypes particle = EnumParticleTypes.CRIT_MAGIC;
    public ItemStackHandler inventory = new ItemStackHandler(1) {
        @Override
        public int getSlotLimit(int slot) {
            return 3;
        }
    };
    public int tickCount;
    public long lastChangeTime;
    public float manaCount;

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("inventory", inventory.serializeNBT());
        compound.setInteger("tickCount", tickCount);
        compound.setLong("lastChangeTime", lastChangeTime);
        compound.setFloat("maganCount", manaCount);
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        inventory.deserializeNBT(compound.getCompoundTag("inventory"));
        tickCount = compound.getInteger("tickCount");
        lastChangeTime = compound.getInteger("lastChangeTime");
        manaCount = compound.getFloat("maganCount");
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
            lastChangeTime = world.getTotalWorldTime();
            PacketHandler.sendToAllAround(world, pos, 64, new PacketUpdatePurifier(TileEntityPurifier.this));
        }
        if (!(PurifierRecipes.getRecipeForInput(inventory.getStackInSlot(0)) == null)) {
            if (inventory.getStackInSlot(0).getCount() == PurifierRecipes.getRecipeForInput(inventory.getStackInSlot(0)).itemCount) {
                tickCount++;
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
        Elementaristics.proxy.generateGenericParticles(world, getPos().getX() + 0.1, getPos().getY() + 0.75, getPos().getZ() + 0.9, 0.03, -0.005, -0.03, 14247, 2, 18, 0F, true, false);
        Elementaristics.proxy.generateGenericParticles(world, getPos().getX() + 0.9, getPos().getY() + 0.75, getPos().getZ() + 0.1, -0.03, -0.005, 0.03, 14247, 2, 18, 0F, true, false);
        Elementaristics.proxy.generateGenericParticles(world, getPos().getX() + 0.9, getPos().getY() + 0.75, getPos().getZ() + 0.9, -0.03, -0.005, -0.03, 14247, 2, 18, 0F, true, false);
        Elementaristics.proxy.generateGenericParticles(world, getPos().getX() + 0.1, getPos().getY() + 0.75, getPos().getZ() + 0.1, 0.03, -0.005, 0.03, 14247, 2, 18, 0F, true, false);

    }

}
