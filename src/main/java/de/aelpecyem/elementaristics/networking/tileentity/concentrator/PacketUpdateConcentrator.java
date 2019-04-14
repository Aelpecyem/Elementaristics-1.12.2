package de.aelpecyem.elementaristics.networking.tileentity.concentrator;

import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityConcentrator;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketUpdateConcentrator implements IMessage {
    private BlockPos pos;
    private ItemStack stack;
    private ItemStack stackInfusing;
    private long lastChangeTime;

    public PacketUpdateConcentrator(BlockPos pos, ItemStack stack, ItemStack stackInfusing, long lastChangeTime) {
        this.pos = pos;
        this.stack = stack;
        this.stackInfusing = stackInfusing;
        this.lastChangeTime = lastChangeTime;
    }

    public PacketUpdateConcentrator(TileEntityConcentrator te) {
        this(te.getPos(), te.inventory.getStackInSlot(0), te.inventory.getStackInSlot(1), te.lastChangeTime);
    }

    public PacketUpdateConcentrator() {
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        ByteBufUtils.writeItemStack(buf, stack);
        ByteBufUtils.writeItemStack(buf, stackInfusing);
        buf.writeLong(lastChangeTime);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        stack = ByteBufUtils.readItemStack(buf);
        stackInfusing = ByteBufUtils.readItemStack(buf);
        lastChangeTime = buf.readLong();
    }

    public static class Handler implements IMessageHandler<PacketUpdateConcentrator, IMessage> {

        @Override
        public IMessage onMessage(PacketUpdateConcentrator message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                TileEntityConcentrator te = (TileEntityConcentrator) Minecraft.getMinecraft().world.getTileEntity(message.pos);
                if (te != null) {
                    te.inventory.setStackInSlot(0, message.stack);

                    te.inventory.setStackInSlot(1, message.stackInfusing);

                    te.lastChangeTime = message.lastChangeTime;
                }

            });
            return null;
        }

    }
}
