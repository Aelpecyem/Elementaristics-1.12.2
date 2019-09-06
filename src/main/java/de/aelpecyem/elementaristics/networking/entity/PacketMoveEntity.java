package de.aelpecyem.elementaristics.networking.entity;

import de.aelpecyem.elementaristics.Elementaristics;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketMoveEntity implements IMessage {
    int entity;
    float motionX, motionY, motionZ;

    public PacketMoveEntity() {

    }

    public PacketMoveEntity(Entity entity, float motionX, float motionY, float motionZ) {
        this.entity = entity.getEntityId();
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(entity);
        buf.writeFloat(motionX);
        buf.writeFloat(motionY);
        buf.writeFloat(motionZ);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.entity = buf.readInt();
        this.motionX = buf.readFloat();
        this.motionY = buf.readFloat();
        this.motionZ = buf.readFloat();
    }


    public static class Handler implements IMessageHandler<PacketMoveEntity, IMessage> {
        @Override
        public IMessage onMessage(PacketMoveEntity message, MessageContext ctx) {
            Elementaristics.proxy.getThreadListener(ctx).addScheduledTask(() -> {
                if (Elementaristics.proxy.getPlayer(ctx) != null) {
                    Entity entity = Elementaristics.proxy.getPlayer(ctx).world.getEntityByID(message.entity);
                    entity.setVelocity(message.motionX, message.motionY, message.motionZ);
                }
            });

            return null;
        }
    }
}
