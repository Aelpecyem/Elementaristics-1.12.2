package de.aelpecyem.elementaristics.networking.tileentity.tick;

import de.aelpecyem.elementaristics.blocks.tileentity.IHasInventory;
import de.aelpecyem.elementaristics.blocks.tileentity.IHasTickCount;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
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

public class PacketUpdateTickTime implements IMessage {
    private BlockPos pos;
    private int tickTime;


    public PacketUpdateTickTime(BlockPos pos, int tickTime) {
        this.pos = pos;
        this.tickTime = tickTime;
    }

    public PacketUpdateTickTime(TileEntity te, int tickTime) {
        this(te.getPos(), tickTime);
    }

    public PacketUpdateTickTime() {
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        buf.writeInt(tickTime);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        tickTime = buf.readInt();
    }

    public static class Handler implements IMessageHandler<PacketUpdateTickTime, IMessage> {

        @Override
        public IMessage onMessage(PacketUpdateTickTime message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                TileEntity te = Minecraft.getMinecraft().world.getTileEntity(message.pos);
                if (te != null && te instanceof IHasTickCount) {
                    ((IHasTickCount) te).setTickCount(message.tickTime);
                }

            });
            return null;
        }
    }
}
