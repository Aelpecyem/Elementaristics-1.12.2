package de.aelpecyem.elementaristics.networking.tileentity.deities;

import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityAltar;
import de.aelpecyem.elementaristics.blocks.tileentity.pantheon.TileEntityDeityShrine;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import scala.util.control.Exception;

public class PacketUpdateDeity implements IMessage {
    private BlockPos pos;
    private String deityBound;
    private boolean isStatue;
    private String unusedString;
    private boolean unusedBool;
    private int unusedInt;
    private float unusedFloat;


    public PacketUpdateDeity(BlockPos pos, String deityBound, boolean isStatue, String unusedString, boolean unusedBool, int unusedInt, float unusedFloat) {
        this.pos = pos;
        this.deityBound = deityBound;
        this.isStatue = isStatue;
        this.unusedString = unusedString;
        this.unusedBool = unusedBool;
        this.unusedInt = unusedInt;
        this.unusedFloat = unusedFloat;

    }

    public PacketUpdateDeity(TileEntityDeityShrine te) {
        this(te.getPos(), te.deityBound, te.isStatue, te.unusedString, te.unusedBool, te.unusedInt, te.unusedFloat);
    }

    public PacketUpdateDeity() {
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());

        ByteBufUtils.writeUTF8String(buf, deityBound);
        buf.writeBoolean(isStatue);
        ByteBufUtils.writeUTF8String(buf, unusedString);
        buf.writeBoolean(unusedBool);
        buf.writeInt(unusedInt);
        buf.writeFloat(unusedFloat);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());

        deityBound = ByteBufUtils.readUTF8String(buf);
        isStatue = buf.readBoolean();
        unusedString = ByteBufUtils.readUTF8String(buf);
        unusedBool = buf.readBoolean();
        unusedInt = buf.readInt();
        unusedFloat = buf.readFloat();
    }

    public static class Handler implements IMessageHandler<PacketUpdateDeity, IMessage> {

        @Override
        public IMessage onMessage(PacketUpdateDeity message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                TileEntityDeityShrine te = (TileEntityDeityShrine) Minecraft.getMinecraft().world.getTileEntity(message.pos);
                if (te != null) {
                    te.deityBound = message.deityBound;
                    te.isStatue = message.isStatue;
                    te.unusedString = message.unusedString;
                    te.unusedBool = message.unusedBool;
                    te.unusedInt = message.unusedInt;
                    te.unusedFloat = message.unusedFloat;

                }
            });
            return null;
        }

    }
}
