package de.aelpecyem.elementaristics.networking.cap;

import de.aelpecyem.elementaristics.capability.chunk.ChunkCapProvider;
import de.aelpecyem.elementaristics.capability.chunk.IChunkCapabilities;
import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CapabilityChunkSync implements IMessage {
    private int soulId;
    private int chunkX, chunkZ;

    public CapabilityChunkSync() {
        this.soulId = 0;
        this.chunkX = 0;
        this.chunkZ = 0;

    }

    public CapabilityChunkSync(int chunkX, int chunkZ, int influenceId) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.soulId = influenceId;
    }


    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(chunkX);
        buf.writeInt(chunkZ);
        buf.writeInt(soulId);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        chunkX = buf.readInt();
        chunkZ = buf.readInt();
        soulId = buf.readInt();
    }

    public static class Handler implements IMessageHandler<CapabilityChunkSync, IMessage> {

        @Override
        public IMessage onMessage(CapabilityChunkSync message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                IChunkCapabilities c = Minecraft.getMinecraft().world.getChunkFromChunkCoords(message.chunkX, message.chunkZ).getCapability(ChunkCapProvider.ELEMENTARISTICS_CAP, null);
                c.setInfluenceId(message.soulId);
            });
            return null;
        }

    }
}
