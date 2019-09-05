package de.aelpecyem.elementaristics.blocks.tileentity;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.init.ModBlocks;
import de.aelpecyem.elementaristics.items.base.ItemGoldenThread;
import de.aelpecyem.elementaristics.networking.PacketHandler;
import de.aelpecyem.elementaristics.networking.tileentity.goldenthread.PacketUpdateGoldenThread;
import de.aelpecyem.elementaristics.networking.tileentity.inventory.PacketUpdateInventory;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.List;


public class TileEntityGoldenThread extends TileEntity implements ITickable, IHasInventory {
    public ItemStackHandler inventory = new ItemStackHandler(1) {
        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }
    };
    public static final int MAX_CHARGE = 50;
    public int charge; //have a charge, a set element etc.
    public int aspect; //-> set with the item block (which will probably be separate from the block)
    public int activationStage;

    private float rotation, height;
    public int animPhase;

    private static final ResourceLocation STRUCTURE = new ResourceLocation(Elementaristics.MODID, "thread_golden");

    /*
    --------------THE IDEA-------------
    This block will be set in the Mind, and must be activated with a special incantation (?).
    Once activated, the block (which is only rendered as TESR, see below why, will spawn many stronger elementals (not implemented yet)-
    This may also somehow keep the player in the boundaries.
    For each elemental killed, the charge count increases, which will cause the block to spin faster and faster and emit more particles etc.
    Once that is done, the block will generate a new structure (?) which basically works like an anchor back to reality. Once the block in the center is used (the entire structure may not be destroyed, which is why a huge empty space is needed),
    the user will ascend, but the block will remain (it will just change a property). The block is also very important for the god ascension.
    Furthermore, the block (which is golden) may be bound to an incantation for a rite, which needs to incantation (player throws it on the altar) and will teleport all players to the Golden Thread.
     */

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("inventory", inventory.serializeNBT());
        compound.setInteger("charge", charge);
        compound.setInteger("aspect", aspect);
        compound.setInteger("activationStage", activationStage);
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        inventory.deserializeNBT(compound.getCompoundTag("inventory"));
        charge = compound.getInteger("charge");
        aspect = compound.getInteger("aspect");
        activationStage = compound.getInteger("activationStage");
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
    public void update() {
        if (!world.isRemote) {
            PacketHandler.sendToAllAround(world, pos, 64, new PacketUpdateInventory(this, inventory));
            PacketHandler.sendToAllAround(world, pos, 64, new PacketUpdateGoldenThread(this));
        }
        if (activationStage < 1) {
            if (world.rand.nextInt(40) == 1) {
                if (!world.isRemote && !ItemGoldenThread.hasSpace(world, pos, null)) {
                    ModBlocks.block_golden_thread.breakBlock(world, pos, world.getBlockState(pos));
                    world.setBlockState(pos, Blocks.AIR.getDefaultState());
                    return;
                }
            }
            if (charge < MAX_CHARGE) { // test value
                //spawn elementals depending on the block's aspect... the charge count of the block will be increased by the elementals on their own
            } else {
                List<EntityPlayer> players = world.getEntitiesWithinAABB(EntityPlayer.class, Block.FULL_BLOCK_AABB.grow(10).offset(pos));
                for (EntityPlayer player : players) {
                    System.out.println("mawjhwahwawf");
                    player.knockBack(player, 6, pos.getX() - player.posX, pos.getZ() - player.posZ);
                }
                charge++;
                if (charge > MAX_CHARGE + 60) {
                    if (!world.isRemote)
                        generateGoldenThread();
                    activationStage = 1;
                }



                //the tile entity will change, but will not be replaced
            }
        }
    }

    public void generateGoldenThread() {
        final PlacementSettings settings = new PlacementSettings().setRotation(Rotation.NONE);
        final Template template = world.getSaveHandler().getStructureTemplateManager().getTemplate(world.getMinecraftServer(), STRUCTURE);
        template.addBlocksToWorld(world, pos.add(-6, -1, -6), settings);
    }

    @Override
    public ItemStackHandler getInventory() {
        return inventory;
    }

    @SideOnly(Side.CLIENT)
    public float getRotation() {
        return rotation;
    }

    @SideOnly(Side.CLIENT)
    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    @SideOnly(Side.CLIENT)
    public float getHeight() {
        return height;
    }

    @SideOnly(Side.CLIENT)
    public void setHeight(float height) {
        this.height = height;
    }

    public int getCharge() {
        return charge;
    }

    public void setCharge(int charge) {
        this.charge = charge;
    }

    public int getAspect() {
        return aspect;
    }

    public void setAspect(int aspect) {
        this.aspect = aspect;
    }

    public int getActivationStage() {
        return activationStage;
    }

    public void setActivationStage(int activationStage) {
        this.activationStage = activationStage;
    }

    public int getAnimPhase() {
        return animPhase;
    }

    public void setAnimPhase(int animPhase) {
        this.animPhase = animPhase;
    }
}
