package de.aelpecyem.elementaristics.networking.tileentity.inventory;

import de.aelpecyem.elementaristics.blocks.tileentity.IHasInventory;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.List;

public class PacketUpdateInventory implements IMessage {
    private BlockPos pos;
    private int id = -1;
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

    public PacketUpdateInventory(Entity e, ItemStackHandler inventory) {
        this.id = e.getEntityId();
        pos = null;
        this.slotCount = inventory.getSlots();
        for (int i = 0; i < slotCount; i++) {
            stacks.add(i, inventory.getStackInSlot(i));
        }
    }

    public PacketUpdateInventory() {
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(id);
        if (id == -1) {
            buf.writeLong(pos.toLong());
        }
        buf.writeInt(slotCount);
        for (int i = 0; i < slotCount; i++)
            ByteBufUtils.writeItemStack(buf, stacks.get(i));

    }

    @Override
    public void fromBytes(ByteBuf buf) {
        id = buf.readInt();
        if (id == -1) {
            pos = BlockPos.fromLong(buf.readLong());
        }

        slotCount = buf.readInt();
        for (int i = 0; i < slotCount; i++)
            stacks.add(i, ByteBufUtils.readItemStack(buf));
    }

    public static class Handler implements IMessageHandler<PacketUpdateInventory, IMessage> {

        @Override
        public IMessage onMessage(PacketUpdateInventory message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                if (message.pos != null) {
                    if (Minecraft.getMinecraft().world.getTileEntity(message.pos) instanceof IHasInventory) {
                        TileEntity te = Minecraft.getMinecraft().world.getTileEntity(message.pos);
                        for (int i = 0; i < message.slotCount; i++)
                            ((IHasInventory) te).getInventory().setStackInSlot(i, message.stacks.get(i));
                    }
                }
                if (message.id != -1) {
                    Entity e = Minecraft.getMinecraft().world.getEntityByID(message.id);
                    if (e instanceof IHasInventory) {
                        for (int i = 0; i < message.slotCount; i++)
                            ((IHasInventory) e).getInventory().setStackInSlot(i, message.stacks.get(i));
                    }
                }

            });
            return null;
        }
    }
}
