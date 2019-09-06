package de.aelpecyem.elementaristics.networking.entity.nexus;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.entity.nexus.EntityDimensionalNexus;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSyncNexus implements IMessage {
    int entity;
    int ticks;
    String rite;

    public PacketSyncNexus() {

    }

    public PacketSyncNexus(EntityDimensionalNexus nexus) {
        this.entity = nexus.getEntityId();
        this.ticks = nexus.getRiteTicks();
        ;
        this.rite = nexus.getRiteString();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(entity);
        buf.writeInt(ticks);
        ByteBufUtils.writeUTF8String(buf, rite);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.entity = buf.readInt();
        this.ticks = buf.readInt();
        this.rite = ByteBufUtils.readUTF8String(buf);
    }


    public static class Handler implements IMessageHandler<PacketSyncNexus, IMessage> {
        @Override
        public IMessage onMessage(PacketSyncNexus message, MessageContext ctx) {
            Elementaristics.proxy.getThreadListener(ctx).addScheduledTask(() -> {
                Entity nexus = Elementaristics.proxy.getPlayer(ctx).world.getEntityByID(message.entity);
                if (nexus instanceof EntityDimensionalNexus) {
                    ((EntityDimensionalNexus) nexus).setRiteTicks(message.ticks);
                    ((EntityDimensionalNexus) nexus).setRiteString(message.rite);
                }
            });

            return null;
        }
    }
}
