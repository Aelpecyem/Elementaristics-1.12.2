package de.aelpecyem.elementaristics.networking.tileentity.purifier;

import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityPurifier;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketUpdatePurifier implements IMessage {
    private BlockPos pos;
    private ItemStack stack;
    private long lastChangeTime;

    public PacketUpdatePurifier(BlockPos pos, ItemStack stack, long lastChangeTime) {
        this.pos = pos;
        this.stack = stack;
        this.lastChangeTime = lastChangeTime;
    }

    public PacketUpdatePurifier(TileEntityPurifier te) {
        this(te.getPos(), te.inventory.getStackInSlot(0), te.lastChangeTime);
    }

    public PacketUpdatePurifier() {
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        ByteBufUtils.writeItemStack(buf, stack);
        buf.writeLong(lastChangeTime);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        stack = ByteBufUtils.readItemStack(buf);
        lastChangeTime = buf.readLong();
    }

    public static class Handler implements IMessageHandler<PacketUpdatePurifier, IMessage> {

        @Override
        public IMessage onMessage(PacketUpdatePurifier message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                TileEntityPurifier te = (TileEntityPurifier) Minecraft.getMinecraft().world.getTileEntity(message.pos);
                if (te != null) {
                    te.inventory.setStackInSlot(0, message.stack);

                    te.lastChangeTime = message.lastChangeTime;
                }
            });
            return null;
        }

    }
}
