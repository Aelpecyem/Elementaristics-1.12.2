package de.aelpecyem.elementaristics.networking.cap;

import de.aelpecyem.elementaristics.capability.chunk.ChunkCapProvider;
import de.aelpecyem.elementaristics.capability.chunk.IChunkCapabilities;
import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CapabilityChunkSync implements IMessage{
    private int soulId;
    private long pos;

    public CapabilityChunkSync(){
        this.soulId = 0;
        this.pos = 0;

    }

    public CapabilityChunkSync(BlockPos pos, int influenceId) {
       this.pos = pos.toLong();
       this.soulId = influenceId;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(soulId);
        buf.writeLong(pos);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        soulId = buf.readInt();
        pos = buf.readLong();
    }

    public static class Handler implements IMessageHandler<CapabilityChunkSync, IMessage> {

        @Override
        public IMessage onMessage(CapabilityChunkSync message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                IChunkCapabilities c = Minecraft.getMinecraft().world.getChunkFromBlockCoords(BlockPos.fromLong(message.pos)).getCapability(ChunkCapProvider.ELEMENTARISTICS_CAP, null);
                c.setInfluenceId(message.soulId);
            });
            return null;
        }

    }
}
