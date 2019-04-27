package de.aelpecyem.elementaristics.networking.tileentity.energy.generatorCombustion;

import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityPurifier;
import de.aelpecyem.elementaristics.blocks.tileentity.energy.TileEntityGeneratorArcaneCombustion;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketUpdateCombustionGenerator implements IMessage {
    private BlockPos pos;
    private ItemStack stack;
    private int tickCount;

    public PacketUpdateCombustionGenerator(BlockPos pos, ItemStack stack, int tickCount) {
        this.pos = pos;
        this.stack = stack;
        this.tickCount = tickCount;
    }

    public PacketUpdateCombustionGenerator(TileEntityGeneratorArcaneCombustion te) {
        this(te.getPos(), te.inventory.getStackInSlot(0), te.tickCount);
    }

    public PacketUpdateCombustionGenerator() {
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        ByteBufUtils.writeItemStack(buf, stack);
        buf.writeInt(tickCount);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        stack = ByteBufUtils.readItemStack(buf);
        tickCount = buf.readInt();
    }

    public static class Handler implements IMessageHandler<PacketUpdateCombustionGenerator, IMessage> {

        @Override
        public IMessage onMessage(PacketUpdateCombustionGenerator message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                TileEntityGeneratorArcaneCombustion te = (TileEntityGeneratorArcaneCombustion) Minecraft.getMinecraft().world.getTileEntity(message.pos);
                if (te != null) {
                    te.inventory.setStackInSlot(0, message.stack);
                    te.tickCount = message.tickCount;
                }
            });
            return null;
        }

    }
}
