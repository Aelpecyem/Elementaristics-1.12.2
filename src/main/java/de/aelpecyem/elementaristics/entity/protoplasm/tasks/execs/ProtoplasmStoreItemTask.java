package de.aelpecyem.elementaristics.entity.protoplasm.tasks.execs;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.entity.protoplasm.EntityProtoplasm;
import de.aelpecyem.elementaristics.items.base.thaumagral.ItemThaumagral;
import de.aelpecyem.elementaristics.particles.ParticleGeneric;
import de.aelpecyem.elementaristics.util.InventoryUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.command.CommandGive;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.List;

public class ProtoplasmStoreItemTask extends ProtoplasmTask {
    ItemStack stack;
    BlockPos inventoryLocation;
    EnumFacing facing;

    public ProtoplasmStoreItemTask() {
        super("store");
    }

    public ProtoplasmStoreItemTask(ItemStack stackToGet, BlockPos inventoryLocation) {
        super("store");
        this.stack = stackToGet;
        this.inventoryLocation = inventoryLocation;
    }

    public ProtoplasmStoreItemTask(ItemStack stackToGet, BlockPos inventoryLocation, EnumFacing facing) {
        super("store");
        this.stack = stackToGet;
        this.inventoryLocation = inventoryLocation;
        this.facing = facing;
    }


    @Override
    public boolean execute(EntityProtoplasm.AIPerformTasks slimeAI) {
        return continueExecuting(slimeAI);
    }


    @Override
    public boolean continueExecuting(EntityProtoplasm.AIPerformTasks slimeAI) {
        return true;
    }


    private boolean storeItem(EntityProtoplasm.AIPerformTasks slimeAI) {
        if ((stack.isEmpty() || slimeAI.slime.inventory.getStackInSlot(0).isItemEqual(stack)) && slimeAI.slime.getPosition().getDistance(inventoryLocation.getX(), inventoryLocation.getY(), inventoryLocation.getZ()) < 3) {
            if (slimeAI.slime.world.getTileEntity(inventoryLocation) != null && slimeAI.slime.world.getTileEntity(inventoryLocation).hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing)) {
                return drawItemsIntoInventory(slimeAI.slime.world, slimeAI.slime.inventory, slimeAI.slime.world.getTileEntity(inventoryLocation).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null));
            }
        }
        return false;
    }

    private boolean drawItemsIntoInventory(World world, ItemStackHandler from, IItemHandler to) { //this has some dirty coding, but otherwise things would act weird, I may clean this up *someday*
        int slot = InventoryUtil.hasSpace(to, from.extractItem(0, stack.isEmpty() ? 64 : stack.getCount(), true));
        if (slot > -1) {
            from.insertItem(0, to.insertItem(slot, from.extractItem(0, stack.isEmpty() ? 64 : stack.getCount(), false), false), false);
            markDirty(world);
            return true;
        }
        return false;
    }

    private void markDirty(World world) {
        world.getTileEntity(inventoryLocation).markDirty();
    }

    @Override
    public boolean isFinished(EntityProtoplasm.AIPerformTasks slimeAI) {
        return storeItem(slimeAI) || (!stack.isEmpty() && !slimeAI.slime.inventory.getStackInSlot(0).isItemEqual(stack));
    }

    @Override
    public ProtoplasmTask applyAttributes(String[] taskParts) {
        ItemStack stack;
        try {
            stack = new ItemStack(CommandGive.getItemByText(null, taskParts[2]), Integer.valueOf(taskParts[3]), Integer.valueOf(taskParts[4]));
        } catch (NumberInvalidException e) {
            stack = ItemStack.EMPTY;
        }
        return new ProtoplasmStoreItemTask(stack, BlockPos.fromLong(Long.valueOf(taskParts[1])), EnumFacing.byName(taskParts[5]));
    }

    @Override
    public String writeAsString() {
        return name + "," + inventoryLocation.toLong() + "," + stack.getItem().getRegistryName().toString() + "," + stack.getCount() + "," + stack.getMetadata() + "," + (facing == null ? "null" : facing.toString()) + ";";
    }

    @Override
    public EnumActionResult setTask(ItemStack stack, ItemThaumagral item, EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (hand == EnumHand.MAIN_HAND) {
            if (worldIn.getTileEntity(pos) != null) {
                if (worldIn.getTileEntity(pos).hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing)) {
                    item.appendTask(stack, new ProtoplasmStoreItemTask(player.getHeldItemOffhand(), pos), player);
                    return EnumActionResult.SUCCESS;
                }
            }
            if (player.world.isRemote) {
                player.sendStatusMessage(new TextComponentString(TextFormatting.RED + I18n.format("message.protoplasm.task_need_inventory")), false);
            }
        }
        return EnumActionResult.FAIL;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public String getHudDescription() {
        return stack.isEmpty() ? I18n.format("hud.store.any") : I18n.format("hud.store") + " " + stack.getDisplayName() + " (" + stack.getCount() + "x)";
    }

    public BlockPos getInventoryLocation() {
        return inventoryLocation;
    }

    public ItemStack getStackToFetch() {
        return stack;
    }

    @Override
    public void getParticles(int indexAt, List<ProtoplasmTask> taskList, @Nullable ProtoplasmTask prevTask, @Nullable ProtoplasmTask nextTask, World world, EntityPlayer player, ItemStack heldItem, ItemThaumagral item) {
        Elementaristics.proxy.generateGenericParticles(new ParticleGeneric(world, inventoryLocation.getX() + world.rand.nextFloat() + world.rand.nextGaussian() / 8, inventoryLocation.getY() + world.rand.nextFloat() + world.rand.nextGaussian() / 8, inventoryLocation.getZ() + world.rand.nextFloat() + world.rand.nextGaussian() / 8, 0, 0, 0, nextTask instanceof ProtoplasmWaitTask ? 2555985 : 13474618, prevTask instanceof ProtoplasmWaitTask ? world.rand.nextFloat() + 0.5F : world.rand.nextFloat() + 0.1F, 100, 0, false, false, true, false, 0, 0, 0));
        super.getParticles(indexAt, taskList, prevTask, nextTask, world, player, heldItem, item);
    }
}
