package de.aelpecyem.elementaristics.networking.tileentity.tunneler;

import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityTunneler;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketUpdateTunneler implements IMessage {
    private BlockPos pos;
    private ItemStack stack;
    private ItemStack stackModule;
    private long lastChangeTime;
    private int tickCount;

    public PacketUpdateTunneler(BlockPos pos, ItemStack stack, ItemStack stackModule, long lastChangeTime, int tickCount) {
        this.pos = pos;
        this.stack = stack;
        this.stackModule = stackModule;
        this.lastChangeTime = lastChangeTime;
        this.tickCount = tickCount;
    }

    public PacketUpdateTunneler(TileEntityTunneler te) {
        this(te.getPos(), te.inventory.getStackInSlot(0), te.inventory.getStackInSlot(1), te.lastChangeTime, te.tickCount);
    }

    public PacketUpdateTunneler() {
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        ByteBufUtils.writeItemStack(buf, stack);
        ByteBufUtils.writeItemStack(buf, stackModule);
        buf.writeLong(lastChangeTime);
        buf.writeInt(tickCount);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        stack = ByteBufUtils.readItemStack(buf);
        stackModule = ByteBufUtils.readItemStack(buf);
        lastChangeTime = buf.readLong();
        tickCount = buf.readInt();
    }

    public static class Handler implements IMessageHandler<PacketUpdateTunneler, IMessage> {

        @Override
        public IMessage onMessage(PacketUpdateTunneler message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                TileEntityTunneler te = (TileEntityTunneler) Minecraft.getMinecraft().world.getTileEntity(message.pos);
                if (te != null) {
                    te.inventory.setStackInSlot(0, message.stack);
                    te.inventory.setStackInSlot(1, message.stackModule);
                    te.lastChangeTime = message.lastChangeTime;
                    te.tickCount = message.tickCount;
                }
            });
            return null;
        }

    }
}
