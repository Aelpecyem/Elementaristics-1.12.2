package de.aelpecyem.elementaristics.networking.player;

import de.aelpecyem.elementaristics.Elementaristics;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketGiveVision implements IMessage {
    String visionName;

    public PacketGiveVision() {

    }

    public PacketGiveVision(String transKey) {
        this.visionName = transKey;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, visionName);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        visionName = ByteBufUtils.readUTF8String(buf);
    }


    public static class Handler implements IMessageHandler<PacketGiveVision, IMessage> {
        @Override
        public IMessage onMessage(PacketGiveVision message, MessageContext ctx) {
            Elementaristics.proxy.getThreadListener(ctx).addScheduledTask(() -> {
                if (Elementaristics.proxy.getPlayer(ctx) != null) {
                    Elementaristics.proxy.giveVision(Elementaristics.proxy.getPlayer(ctx), message.visionName);
                }
            });

            return null;
        }
    }
}
