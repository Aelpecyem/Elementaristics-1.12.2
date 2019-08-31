package de.aelpecyem.elementaristics.networking.tileentity.goldenthread;

import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityGoldenThread;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketUpdateGoldenThread implements IMessage {
    private BlockPos pos;
    private int charge, aspect, activationStage;


    public PacketUpdateGoldenThread(BlockPos pos, int charge, int aspect, int activationStage) {
        this.pos = pos;
        this.charge = charge;
        this.aspect = aspect;
        this.activationStage = activationStage;
    }

    public PacketUpdateGoldenThread(TileEntityGoldenThread te) {
        this(te.getPos(), te.charge, te.aspect, te.activationStage);
    }

    public PacketUpdateGoldenThread() {
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        buf.writeInt(charge);
        buf.writeInt(aspect);
        buf.writeInt(activationStage);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        charge = buf.readInt();
        aspect = buf.readInt();
        activationStage = buf.readInt();
    }

    public static class Handler implements IMessageHandler<PacketUpdateGoldenThread, IMessage> {

        @Override
        public IMessage onMessage(PacketUpdateGoldenThread message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                if (Minecraft.getMinecraft().world.getTileEntity(message.pos) instanceof TileEntityGoldenThread) {
                    TileEntityGoldenThread te = (TileEntityGoldenThread) Minecraft.getMinecraft().world.getTileEntity(message.pos);
                    te.charge = message.charge;
                    te.aspect = message.aspect;
                    te.activationStage = message.activationStage;
                }
            });
            return null;
        }

    }
}
