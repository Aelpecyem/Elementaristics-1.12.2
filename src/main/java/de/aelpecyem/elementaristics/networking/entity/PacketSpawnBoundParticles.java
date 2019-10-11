package de.aelpecyem.elementaristics.networking.entity;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSpawnBoundParticles implements IMessage {
    float x, y, z;
    float entitySize;
    float entityHeight;

    public PacketSpawnBoundParticles(float x, float y, float z, float entitySize, float entityHeight) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.entitySize = entitySize;
        this.entityHeight = entityHeight;
    }

    public PacketSpawnBoundParticles() {

    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeFloat(x);
        buf.writeFloat(y);
        buf.writeFloat(z);
        buf.writeFloat(entitySize);
        buf.writeFloat(entityHeight);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        x = buf.readFloat();
        y = buf.readFloat();
        z = buf.readFloat();
        entitySize = buf.readFloat();
        entityHeight = buf.readFloat();
    }

    public static class Handler implements IMessageHandler<PacketSpawnBoundParticles, IMessage> {

        @Override
        public IMessage onMessage(PacketSpawnBoundParticles message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                World world = Minecraft.getMinecraft().player.world;
                double motionX = world.rand.nextGaussian() * 0.01D;
                double motionY = world.rand.nextGaussian() * 0.01D;
                double motionZ = world.rand.nextGaussian() * 0.01D;
                Elementaristics.proxy.generateGenericParticles(world, message.x + world.rand.nextFloat() * message.entitySize
                                * 2.0F - message.entitySize,
                        message.y + 0.5D + world.rand.nextFloat()
                                * message.entityHeight,
                        message.z + world.rand.nextFloat() * message.entitySize
                                * 2.0F - message.entitySize, motionX, motionY, motionZ, Aspects.body.getColor(), 3, 100, 0, false, false, 0.8F, true);
                Elementaristics.proxy.generateGenericParticles(world, message.x + world.rand.nextFloat() * message.entitySize
                                * 2.0F - message.entitySize,
                        message.y + 0.5D + world.rand.nextFloat()
                                * message.entityHeight,
                        message.z + world.rand.nextFloat() * message.entitySize
                                * 2.0F - message.entitySize, motionX, motionY, motionZ, Aspects.earth.getColor(), 3, 100, 0, false, false, 0.8F, true);

            });
            return null;
        }

    }
}
