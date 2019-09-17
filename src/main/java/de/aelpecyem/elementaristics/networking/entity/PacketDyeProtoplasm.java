package de.aelpecyem.elementaristics.networking.entity;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.entity.protoplasm.EntityProtoplasm;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketDyeProtoplasm implements IMessage {
    int entity;
    int color;

    public PacketDyeProtoplasm() {

    }

    public PacketDyeProtoplasm(EntityProtoplasm slime, int color) {
        this.entity = slime.getEntityId();
        this.color = color;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(entity);
        buf.writeInt(color);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.entity = buf.readInt();
        this.color = buf.readInt();
    }


    public static class Handler implements IMessageHandler<PacketDyeProtoplasm, IMessage> {
        @Override
        public IMessage onMessage(PacketDyeProtoplasm message, MessageContext ctx) {
            Elementaristics.proxy.getThreadListener(ctx).addScheduledTask(() -> {
                Entity protoplasm = Elementaristics.proxy.getPlayer(ctx).world.getEntityByID(message.entity);
                if (protoplasm instanceof EntityProtoplasm) {
                    ((EntityProtoplasm) protoplasm).addColor(message.color);
                }
            });

            return null;
        }
    }
}
