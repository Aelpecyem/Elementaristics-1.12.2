package de.aelpecyem.elementaristics.networking.tileentity.altar;

import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityAltar;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketUpdateAltar implements IMessage {
    private BlockPos pos;
    private String currentRite;


    public PacketUpdateAltar(BlockPos pos, String currentRite) {
        this.pos = pos;
        this.currentRite = currentRite;
    }

    public PacketUpdateAltar(TileEntityAltar te) {
        this(te.getPos(), te.currentRite);
    }

    public PacketUpdateAltar() {
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        ByteBufUtils.writeUTF8String(buf, currentRite);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        currentRite = ByteBufUtils.readUTF8String(buf);
    }

    public static class Handler implements IMessageHandler<PacketUpdateAltar, IMessage> {

        @Override
        public IMessage onMessage(PacketUpdateAltar message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                if (Minecraft.getMinecraft().world.getTileEntity(message.pos) instanceof TileEntityAltar) {
                    TileEntityAltar te = (TileEntityAltar) Minecraft.getMinecraft().world.getTileEntity(message.pos);
                    te.currentRite = message.currentRite;
                }
            });
            return null;
        }

    }
}
