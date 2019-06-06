package de.aelpecyem.elementaristics.networking.tileentity.energy;

import de.aelpecyem.elementaristics.blocks.tileentity.energy.TileEntityRedstoneEmulator;
import de.aelpecyem.elementaristics.blocks.tileentity.energy.TileEntityRedstoneTransmitter;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketUpdateEmulator implements IMessage {
    private boolean active;
    private BlockPos pos;

    public PacketUpdateEmulator() {
        active = false;
    }

    public PacketUpdateEmulator(TileEntityRedstoneEmulator te) {
        active = te.activated;
        pos = te.getPos();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(active);
        buf.writeLong(pos.toLong());
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        active = buf.readBoolean();
        pos = BlockPos.fromLong(buf.readLong());
    }

    public static class Handler implements IMessageHandler<PacketUpdateEmulator, IMessage> {

        @Override
        public IMessage onMessage(PacketUpdateEmulator message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                TileEntity te = Minecraft.getMinecraft().world.getTileEntity(message.pos);
                if (te != null && te instanceof TileEntityRedstoneEmulator) {
                    ((TileEntityRedstoneEmulator) te).activated = message.active;
                }
            });
            return null;
        }

    }
}
