package de.aelpecyem.elementaristics.networking.other;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketMarkBlock implements IMessage {
    BlockPos pos;

    public PacketMarkBlock() {

    }

    public PacketMarkBlock(BlockPos pos) {
        this.pos = pos;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.pos = BlockPos.fromLong(buf.readLong());
    }


    public static class Handler implements IMessageHandler<PacketMarkBlock, IMessage> {
        @Override
        public IMessage onMessage(PacketMarkBlock message, MessageContext ctx) {
            Elementaristics.proxy.getThreadListener(ctx).addScheduledTask(() -> {
                Elementaristics.proxy.generateGenericParticles(Minecraft.getMinecraft().world, message.pos, Aspects.mana.getColor(), 1.5F, 120, 0, false, false);
            });

            return null;
        }
    }
}
