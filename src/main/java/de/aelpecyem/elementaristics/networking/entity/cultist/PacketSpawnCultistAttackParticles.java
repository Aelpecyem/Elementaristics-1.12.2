package de.aelpecyem.elementaristics.networking.entity.cultist;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.entity.EntityCultist;
import de.aelpecyem.elementaristics.particles.ParticleGeneric;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSpawnCultistAttackParticles implements IMessage {
    int entityId;

    public PacketSpawnCultistAttackParticles(EntityCultist cultist) {
        this.entityId = cultist.getEntityId();
    }

    public PacketSpawnCultistAttackParticles() {

    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(entityId);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        entityId = buf.readInt();
    }

    public static class Handler implements IMessageHandler<PacketSpawnCultistAttackParticles, IMessage> {

        @Override
        public IMessage onMessage(PacketSpawnCultistAttackParticles message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                EntityCultist cultist = (EntityCultist) Elementaristics.proxy.getPlayer(ctx).world.getEntityByID(message.entityId);
                double x = cultist.posX - MathHelper.sin(cultist.rotationYawHead * 0.017453292F) * MathHelper.cos(cultist.rotationPitch * 0.017453292F);
                double y = cultist.posY + cultist.getEyeHeight() + -MathHelper.sin(cultist.rotationPitch * 0.017453292F);
                double z = cultist.posZ + MathHelper.cos(cultist.rotationYawHead * 0.017453292F) * MathHelper.cos(cultist.rotationPitch * 0.017453292F);
                Elementaristics.proxy.generateGenericParticles(new ParticleGeneric(cultist.world, x + cultist.getRNG().nextGaussian() / 4, y + cultist.getRNG().nextGaussian() / 4, z + cultist.getRNG().nextGaussian() / 4, 0, 0, 0, cultist.getAspect().getColor(), 1.5F + cultist.getRNG().nextFloat() / 3, 30 + cultist.getRNG().nextInt(40), 0F, false, false, true, true, x, y, z));
            });
            return null;
        }

    }
}
