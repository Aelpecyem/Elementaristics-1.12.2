package de.aelpecyem.elementaristics.entity.protoplasm.tasks.execs;

import com.mojang.realmsclient.gui.ChatFormatting;
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

public class ProtoplasmTakeItemTask extends ProtoplasmTask {
    ItemStack stack;
    BlockPos inventoryLocation;
    EnumFacing facing;

    private static final boolean SIMULATE = true;
    private static final boolean EXECUTE = false;


    public ProtoplasmTakeItemTask() {
        super("take");
    }

    public ProtoplasmTakeItemTask(ItemStack stackToGet, BlockPos inventoryLocation) {
        super("take");
        this.stack = stackToGet;
        this.inventoryLocation = inventoryLocation;
    }

    public ProtoplasmTakeItemTask(ItemStack stackToGet, BlockPos inventoryLocation, EnumFacing facing) {
        super("take"); //fixme, reduces item stack :(
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

    private boolean takeItem(EntityProtoplasm.AIPerformTasks slimeAI) {
        if (slimeAI.slime.getPosition().getDistance(inventoryLocation.getX(), inventoryLocation.getY(), inventoryLocation.getZ()) < 3) {
            if (slimeAI.slime.world.getTileEntity(inventoryLocation) != null && slimeAI.slime.world.getTileEntity(inventoryLocation).hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)) {
                return drawItemsFromInventory(slimeAI.slime.world, slimeAI.slime.inventory, slimeAI.slime.world.getTileEntity(inventoryLocation).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null));
            }
        }
        return false;
    }

    private boolean drawItemsFromInventory(World world, ItemStackHandler to, IItemHandler from) { //this has some dirty coding, but otherwise things would act weird, I may clean this up *someday*
        ItemStack stackGrab = getStackToGrab(to, from);
        int slot = InventoryUtil.hasStack(from, stackGrab);
        if (InventoryUtil.hasSpace(to, stackGrab) > -1 && slot > -1) {
            ItemStack stackFilled = from.extractItem(slot, stackGrab.getCount(), false).copy();
            if (to.getStackInSlot(0).isEmpty() || to.getStackInSlot(0).isItemEqual(stackFilled)) {//InventoryUtil.areStacksEqual(to.getStackInSlot(0), stackFilled)) {
                to.insertItem(0, stackFilled, false);
            }
            markDirty(world);
            return true;
        }
        return false;
    }

    private ItemStack getStackToGrab(IItemHandler to, IItemHandler from) {
        if (stack.isEmpty()) {
            if (to.getStackInSlot(0).isEmpty()) {
                for (int i = 0; i < from.getSlots(); i++) {
                    if (!from.getStackInSlot(i).isEmpty()) {
                        return from.extractItem(i, to.getStackInSlot(0).getMaxStackSize() - to.getStackInSlot(0).getCount(), true);//do stock stuff later
                    }
                }
            } else {
                for (int i = 0; i < from.getSlots(); i++) {
                    if (!from.getStackInSlot(i).isEmpty() && from.getStackInSlot(i).isItemEqual(to.getStackInSlot(0))) {
                        return from.extractItem(i, to.getStackInSlot(0).getMaxStackSize() - to.getStackInSlot(0).getCount(), true);//do stock stuff later
                    }
                }
            }
        } else {
            for (int i = 0; i < from.getSlots(); i++) {
                if (from.getStackInSlot(i).isItemEqual(stack)) {
                    return from.extractItem(i, stack.getCount(), true);//do stock stuff later
                }
            }
        }
        return ItemStack.EMPTY;
    }

    private void markDirty(World world) {
        world.getTileEntity(inventoryLocation).markDirty();
    }

    @Override
    public boolean isFinished(EntityProtoplasm.AIPerformTasks slimeAI) {
        return takeItem(slimeAI);
    }

    @Override
    public ProtoplasmTask applyAttributes(String[] taskParts) {
        ItemStack stack;
        try {
            stack = new ItemStack(CommandGive.getItemByText(null, taskParts[2]), Integer.valueOf(taskParts[3]), Integer.valueOf(taskParts[4]));
        } catch (NumberInvalidException e) {
            stack = ItemStack.EMPTY;
        }
        return new ProtoplasmTakeItemTask(stack, BlockPos.fromLong(Long.valueOf(taskParts[1])), EnumFacing.byName(taskParts[5]));
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
                    ItemStack stackSet = player.getHeldItemOffhand().copy();
                    if (item.getWIPTask(stack) != "0") {
                        stackSet.setCount(64);
                    }
                    item.appendTask(stack, new ProtoplasmTakeItemTask(stackSet, pos), player);
                    return EnumActionResult.SUCCESS;
                }
            } else {
                item.setWIPTask(stack, item.getWIPTask(stack) == "0" ? "1" : "0");
                if (worldIn.isRemote) {
                    player.sendStatusMessage(new TextComponentString((item.getWIPTask(stack) == "0" ? ChatFormatting.RED : ChatFormatting.GREEN) + (item.getWIPTask(stack) == "0" ? "message.protoplasm.set_full_stack_false" : "message.protoplasm.set_full_stack_true")), true);//PacketHandler.sendTo(playerIn, new PacketMessage("message.task_timer"));
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
        return stack.isEmpty() ? I18n.format("hud.fetch.any") : I18n.format("hud.fetch") + " " + stack.getDisplayName() + " (" + stack.getCount() + "x)";
    }

    public BlockPos getInventoryLocation() {
        return inventoryLocation;
    }

    public ItemStack getStackToFetch() {
        return stack;
    }

    @Override
    public void getParticles(int indexAt, List<ProtoplasmTask> taskList, @Nullable ProtoplasmTask prevTask, @Nullable ProtoplasmTask nextTask, World world, EntityPlayer player, ItemStack heldItem, ItemThaumagral item) {
        Elementaristics.proxy.generateGenericParticles(new ParticleGeneric(world, inventoryLocation.getX() + world.rand.nextFloat() + world.rand.nextGaussian() / 8, inventoryLocation.getY() + world.rand.nextFloat() + world.rand.nextGaussian() / 8, inventoryLocation.getZ() + world.rand.nextFloat() + world.rand.nextGaussian() / 8, 0, 0, 0, nextTask instanceof ProtoplasmWaitTask ? 2555985 : 3119930, prevTask instanceof ProtoplasmWaitTask ? world.rand.nextFloat() + 0.5F : world.rand.nextFloat() + 0.1F, 100, 0, false, false, true, false, 0, 0, 0));
        super.getParticles(indexAt, taskList, prevTask, nextTask, world, player, heldItem, item);
    }
}
