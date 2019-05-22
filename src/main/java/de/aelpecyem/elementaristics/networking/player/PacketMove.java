package de.aelpecyem.elementaristics.networking.player;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.UUID;

public class PacketMove implements IMessage {
    float motionX, motionY, motionZ;

    public PacketMove() {

    }

    public PacketMove(float motionX, float motionY, float motionZ) {
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeFloat(motionX);
        buf.writeFloat(motionY);
        buf.writeFloat(motionZ);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.motionX = buf.readFloat();
        this.motionY = buf.readFloat();
        this.motionZ = buf.readFloat();
    }


    public static class Handler implements IMessageHandler<PacketMove, IMessage> {
        @Override
        public IMessage onMessage(PacketMove message, MessageContext ctx) {
            Elementaristics.proxy.getThreadListener(ctx).addScheduledTask(() -> {
                if (Elementaristics.proxy.getPlayer(ctx) != null) {
                    EntityPlayer player = Elementaristics.proxy.getPlayer(ctx);
                    player.setVelocity(message.motionX, message.motionY, message.motionZ);
                }
            });

            return null;
        }
    }
}
