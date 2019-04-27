package de.aelpecyem.elementaristics.networking.tileentity.pedestallightning;

import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityLightningPedestal;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketUpdateLightningPedestal implements IMessage {
    private BlockPos pos;
    private ItemStack stack;
    private long lastChangeTime;
    private int tickCount;

    public PacketUpdateLightningPedestal(BlockPos pos, ItemStack stack, long lastChangeTime, int tickCount) {
        this.pos = pos;
        this.stack = stack;
        this.lastChangeTime = lastChangeTime;
        this.tickCount = tickCount;
    }

    public PacketUpdateLightningPedestal(TileEntityLightningPedestal te) {
        this(te.getPos(), te.inventory.getStackInSlot(0), te.lastChangeTime, te.tickCount);
    }

    public PacketUpdateLightningPedestal() {
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        ByteBufUtils.writeItemStack(buf, stack);
        buf.writeLong(lastChangeTime);
        buf.writeInt(tickCount);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        stack = ByteBufUtils.readItemStack(buf);
        lastChangeTime = buf.readLong();
        tickCount = buf.readInt();
    }

    public static class Handler implements IMessageHandler<PacketUpdateLightningPedestal, IMessage> {

        @Override
        public IMessage onMessage(PacketUpdateLightningPedestal message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                TileEntityLightningPedestal te = (TileEntityLightningPedestal) Minecraft.getMinecraft().world.getTileEntity(message.pos);
                if (te != null) {
                    te.inventory.setStackInSlot(0, message.stack);
                    te.lastChangeTime = message.lastChangeTime;
                    te.tickCount = message.tickCount;
                }
            });
            return null;
        }

    }
}
