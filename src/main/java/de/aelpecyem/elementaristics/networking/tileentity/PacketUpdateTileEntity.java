package de.aelpecyem.elementaristics.networking.tileentity;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketUpdateTileEntity implements IMessage {
    private BlockPos pos;
    private NBTTagCompound nbt;


    public PacketUpdateTileEntity(BlockPos pos, NBTTagCompound nbt) {
        this.pos = pos;
        this.nbt = nbt;
    }

    public PacketUpdateTileEntity(TileEntity te) {
        this(te.getPos(), te.serializeNBT());
    }

    public PacketUpdateTileEntity() {
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        ByteBufUtils.writeTag(buf, nbt);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        nbt = ByteBufUtils.readTag(buf);
    }

    public static class Handler implements IMessageHandler<PacketUpdateTileEntity, IMessage> {

        @Override
        public IMessage onMessage(PacketUpdateTileEntity message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                if (Minecraft.getMinecraft().world.getTileEntity(message.pos) != null) {
                    Minecraft.getMinecraft().world.getTileEntity(message.pos).deserializeNBT(message.nbt);
                }
            });
            return null;
        }

    }
}
