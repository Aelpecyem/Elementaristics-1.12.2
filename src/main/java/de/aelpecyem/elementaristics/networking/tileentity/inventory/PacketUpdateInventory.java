package de.aelpecyem.elementaristics.networking.tileentity.inventory;

import de.aelpecyem.elementaristics.blocks.tileentity.IHasInventory;
import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityConcentrator;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.util.ArrayList;
import java.util.List;

public class PacketUpdateInventory implements IMessage {
    private BlockPos pos;
    private List<ItemStack> stacks = new ArrayList<>();
    private int slotCount;


    public PacketUpdateInventory(BlockPos pos, ItemStackHandler inventory) {
        this.pos = pos;
        this.slotCount = inventory.getSlots();
        for (int i = 0; i < slotCount; i++) {
            stacks.add(i, inventory.getStackInSlot(i));
        }
    }

    public PacketUpdateInventory(TileEntity te, ItemStackHandler inventory) {
        this(te.getPos(), inventory);
    }

    public PacketUpdateInventory() {
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        buf.writeInt(slotCount);
        for (int i = 0; i < slotCount; i++)
            ByteBufUtils.writeItemStack(buf, stacks.get(i));

    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        slotCount = buf.readInt();
        for (int i = 0; i < slotCount; i++)
            stacks.add(i, ByteBufUtils.readItemStack(buf));
    }

    public static class Handler implements IMessageHandler<PacketUpdateInventory, IMessage> {

        @Override
        public IMessage onMessage(PacketUpdateInventory message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                TileEntity te = Minecraft.getMinecraft().world.getTileEntity(message.pos);
                if (te != null && te instanceof IHasInventory) {
                    for (int i = 0; i < message.slotCount; i++)
                        ((IHasInventory) te).getInventory().setStackInSlot(i, message.stacks.get(i));
                }

            });
            return null;
        }
    }
}
