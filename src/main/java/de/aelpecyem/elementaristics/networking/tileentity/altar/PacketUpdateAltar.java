package de.aelpecyem.elementaristics.networking.tileentity.altar;

import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityAltar;
import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityPurifier;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketUpdateAltar implements IMessage {
    private BlockPos pos;
    private String currentRite;
    private int tickCount;


    public PacketUpdateAltar(BlockPos pos, String currentRite, int tickCount) {
        this.pos = pos;
        this.currentRite = currentRite;
        this.tickCount = tickCount;
    }

    public PacketUpdateAltar(TileEntityAltar te) {
        this(te.getPos(), te.currentRite, te.tickCount);
    }

    public PacketUpdateAltar() {
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        ByteBufUtils.writeUTF8String(buf, currentRite);
        buf.writeInt(tickCount);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        currentRite = ByteBufUtils.readUTF8String(buf);
        tickCount = buf.readInt();
    }

    public static class Handler implements IMessageHandler<PacketUpdateAltar, IMessage> {

        @Override
        public IMessage onMessage(PacketUpdateAltar message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                TileEntityAltar te = (TileEntityAltar) Minecraft.getMinecraft().world.getTileEntity(message.pos);
                if (te != null) {
                    te.tickCount = message.tickCount;
                    te.currentRite = message.currentRite;
                }
            });
            return null;
        }

    }
}
