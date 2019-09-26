package de.aelpecyem.elementaristics.blocks.tileentity.tile;

import de.aelpecyem.elementaristics.blocks.tileentity.blocks.BlockReactor;
import de.aelpecyem.elementaristics.blocks.tileentity.tile.energy.TileEntityEnergy;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import de.aelpecyem.elementaristics.capability.player.souls.Soul;
import de.aelpecyem.elementaristics.networking.PacketHandler;
import de.aelpecyem.elementaristics.recipe.VesselRecipes;
import de.aelpecyem.elementaristics.recipe.base.VesselRecipe;
import de.aelpecyem.elementaristics.util.CapabilityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.UUID;

public class TileEntityVessel extends TileEntityEnergy implements ITickable {

    public ItemStackHandler inventory = new ItemStackHandler(5) {
        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }
    };

    @Override
    public int getMaxEnergy() {
        return 10000;
    }

    @Override
    public int getTransfer(int i) {
        return i == 0 ? 100 : 10;
    }

    public int tickCount;
    public int craftingProcess;
    public static final int CRAFTING_FINISHED = 6000;//once that is done, put out the Golden Thread
    //this will also require E N E R G Y
    /**
     * Modes:
     * 0: Deus Ex Machina shenanigans
     * 1: Forging the Golden Thread
     */
    public int mode = 0;
    public String lastUserUUID = ""; //replace with UUID and write that stuff to nbt etc.

    //Caged Entity Data
    private ResourceLocation cagedEntityId = new ResourceLocation("");
    private NBTTagCompound cagedEntityData = new NBTTagCompound();
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("inventory", inventory.serializeNBT());
        compound.setInteger("tickCount", tickCount);
        compound.setInteger("mode", mode);
        compound.setString("cagedEntityId", cagedEntityId.toString());
        compound.setTag("cagedEntityData", cagedEntityData);
        compound.setString("lastUser", lastUserUUID);
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        inventory.deserializeNBT(compound.getCompoundTag("inventory"));
        tickCount = compound.getInteger("tickCount");
        mode = compound.getInteger("mode");
        cagedEntityId = new ResourceLocation(compound.getString("cagedEntityId"));
        cagedEntityData = compound.getCompoundTag("cagedEntityData");
        lastUserUUID = compound.getString("lastUser");
        super.readFromNBT(compound);
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
    public AxisAlignedBB getRenderBoundingBox() {
        return super.getRenderBoundingBox().grow(1, 2, 1);
    }

    @Override
    public void update() {
        if (!world.isRemote) {
            PacketHandler.syncTile(this);
            if (getLastUser() == null) {
                setLastUser(world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 10, false));
            }
        }
        if (!forgeConstructed()) {
            mode = 1;
        }

        if (getLastUser() != null) {
            if (getUserQualification() > 0 && getUserSoul() != null) {
                if (mode == 1) { //Forging the golden thread; add many exotic crafting materials
                    VesselRecipe recipe = VesselRecipes.getRecipeForInputs(inventory, getCagedEntity());
                    if (recipe != null && storage.extractIfPossible(20)) {
                        craftingProcess++;
                        if (craftingProcess >= CRAFTING_FINISHED) {
                            clearCage();
                            for (int i = 0; i < inventory.getSlots(); i++) {
                                inventory.setStackInSlot(i, ItemStack.EMPTY);
                            }
                            EntityItem item = new EntityItem(world, pos.getX() + 0.5F, pos.getY() + 0.75F, pos.getZ() + 0.5F, recipe.output);
                            if (!world.isRemote) {
                                world.spawnEntity(item);
                            }
                        }
                    }
                    /*if (isValidCultist()  && hasIngredients()){//external class for handling that
                createGoldenThread();
                //ascend?
            }*/
                }
            }
        }

    }

    public boolean forgeConstructed() {
        return false; //check multiblock here
    }

    public int getUserQualification() {
        if (getLastUser() != null && getLastUser().hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
            return getLastUser().getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null).getPlayerAscensionStage(); //probably take in ascension route
        }
        return 0;
    }

    public Soul getUserSoul() {
        if (getLastUser() != null && getLastUser() instanceof EntityPlayer && getLastUser().hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
            return CapabilityUtil.getSoul(getLastUser());
        }
        return null;
    }

    //adding entity is part of the block? entity must be on top, then the block is right clicked

    public EntityLiving getCagedEntity() {
        if (cagedEntityData.hasKey("id")) {//!cagedEntityId.getResourcePath().equals("")) {
            Entity cagedEntity = EntityList.createEntityFromNBT(cagedEntityData, world); //EntityList.createEntityByIDFromName(cagedEntityId, world);
            if (cagedEntity instanceof EntityLiving) {
                return (EntityLiving) cagedEntity;
            }
            /*if (cagedEntity instanceof EntityLiving) {
                cagedEntity.readFromNBT(cagedEntityData);
                return (EntityLiving) cagedEntity;
            }*/
        }
        return null;
    }

    public void setCagedEntity(EntityLiving living) {
        if (living == null) {
            clearCage();
            return;
        }
        if (living.world == null)
            living.world = world;
        cagedEntityData = living.serializeNBT();
    }

    public boolean absorbEntity(EntityLiving living) {
        if (getCagedEntity() == null && living.isNonBoss()) {
            living.setPositionAndRotation(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, getRotationFromFacing(), 0);
            living.rotationYawHead = living.rotationYaw;
            setCagedEntity(living);
            living.setDead();
            return true;
        }
        return false;
    }

    public void clearCage() {
        cagedEntityId = new ResourceLocation("");
        cagedEntityData = new NBTTagCompound();
    }

    public EntityPlayer getLastUser() {
        return lastUserUUID.equals("") ? null : world.getPlayerEntityByUUID(UUID.fromString(lastUserUUID));
    }

    public void setLastUser(EntityPlayer player) {
        if (player != null)
            lastUserUUID = player.getUniqueID().toString();
    }

    public int getRotationFromFacing() {
        return getBlockFacing() == EnumFacing.SOUTH ? 0 : getBlockFacing() == EnumFacing.WEST ? 90 : getBlockFacing() == EnumFacing.NORTH ? 180 : 270;
    }

    public EnumFacing getBlockFacing() {
        return this.world.getBlockState(this.pos).getValue(BlockReactor.FACING);
    }
}



