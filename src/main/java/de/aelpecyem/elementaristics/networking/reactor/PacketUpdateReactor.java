package de.aelpecyem.elementaristics.networking.reactor;

import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityReactor;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketUpdateReactor implements IMessage {
    private BlockPos pos;
    private ItemStack stack;
    private ItemStack stack2;
    private long lastChangeTime;

    public PacketUpdateReactor(BlockPos pos, ItemStack stack, ItemStack stack2, long lastChangeTime) {
        this.pos = pos;
        this.stack = stack;
        this.stack2 = stack2;
        this.lastChangeTime = lastChangeTime;
    }

    public PacketUpdateReactor(TileEntityReactor te) {
        this(te.getPos(), te.inventory.getStackInSlot(0), te.inventory.getStackInSlot(1), te.lastChangeTime);
    }

    public PacketUpdateReactor() {
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        ByteBufUtils.writeItemStack(buf, stack);
        ByteBufUtils.writeItemStack(buf, stack2);
        buf.writeLong(lastChangeTime);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        stack = ByteBufUtils.readItemStack(buf);
        stack2 = ByteBufUtils.readItemStack(buf);
        lastChangeTime = buf.readLong();
    }

    public static class Handler implements IMessageHandler<PacketUpdateReactor, IMessage> {

        @Override
        public IMessage onMessage(PacketUpdateReactor message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                TileEntityReactor te = (TileEntityReactor) Minecraft.getMinecraft().world.getTileEntity(message.pos);
                if (te != null) {
                    te.inventory.setStackInSlot(0, message.stack);
                    te.inventory.setStackInSlot(1, message.stack2);

                    te.lastChangeTime = message.lastChangeTime;
                }
            });
            return null;
        }

    }
}
