package de.aelpecyem.elementaristics.networking.tileentity.pos;

import de.aelpecyem.elementaristics.blocks.tileentity.IHasBoundPosition;
import de.aelpecyem.elementaristics.blocks.tileentity.energy.TileEntityEnergy;
import de.aelpecyem.elementaristics.blocks.tileentity.energy.TileEntityRedstoneTransmitter;
import de.aelpecyem.elementaristics.capability.energy.EnergyCapability;
import de.aelpecyem.elementaristics.networking.tileentity.energy.EnergySync;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PosSync implements IMessage {
    private BlockPos pos, posTo;

    public PosSync() {
    }

    public PosSync(TileEntity te) {
        this.pos = te.getPos();
        if (posTo == null) {
            posTo = pos;
        }
        posTo = ((IHasBoundPosition) te).getPositionBoundTo();

    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        if (posTo == null) {
            posTo = pos;
        }
        buf.writeLong(posTo.toLong());
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        if (posTo == null) {
            posTo = pos;
        }
        posTo = BlockPos.fromLong(buf.readLong());
    }

    public static class Handler implements IMessageHandler<PosSync, IMessage> {

        @Override
        public IMessage onMessage(PosSync message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                TileEntity te = Minecraft.getMinecraft().world.getTileEntity(message.pos);
                if (te != null && te instanceof IHasBoundPosition) {
                    if (message.posTo == null)
                        ((IHasBoundPosition) te).setPositionBoundTo(message.pos);
                    else
                        ((IHasBoundPosition) te).setPositionBoundTo(message.posTo);
                }
            });
            return null;
        }
    }
}