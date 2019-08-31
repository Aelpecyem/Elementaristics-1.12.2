package de.aelpecyem.elementaristics.networking.entity.cultist;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.entity.EntityCultist;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSpawnCultistSpellParticles implements IMessage {
    int entityId;
    int color;

    public PacketSpawnCultistSpellParticles(EntityCultist cultist, int color) {
        this.entityId = cultist.getEntityId();
        this.color = color;
    }

    public PacketSpawnCultistSpellParticles() {

    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeInt(color);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        entityId = buf.readInt();
        color = buf.readInt();
    }

    public static class Handler implements IMessageHandler<PacketSpawnCultistSpellParticles, IMessage> {

        @Override
        public IMessage onMessage(PacketSpawnCultistSpellParticles message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                EntityCultist cultist = (EntityCultist) Elementaristics.proxy.getPlayer(ctx).world.getEntityByID(message.entityId);
                Elementaristics.proxy.generateGenericParticles(cultist, message.color, 0.2F + cultist.getRNG().nextFloat(), 60, 0, false, false);
            });
            return null;
        }

    }
}
