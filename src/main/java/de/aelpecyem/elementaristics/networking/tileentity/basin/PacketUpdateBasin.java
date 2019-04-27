package de.aelpecyem.elementaristics.networking.tileentity.basin;

import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityConcentrator;
import de.aelpecyem.elementaristics.blocks.tileentity.TileEntityInfusionBasin;
import de.aelpecyem.elementaristics.misc.elements.Aspect;
import de.aelpecyem.elementaristics.misc.elements.Aspects;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import org.apache.commons.lang3.ArrayUtils;
import scala.Int;

import java.util.ArrayList;
import java.util.List;

public class PacketUpdateBasin implements IMessage {
    private BlockPos pos;
    private ItemStack stack;
    private List<Integer> aspects = new ArrayList<>();
    private int aspectsSize;
    private int fillCount;
    private long lastChangeTime;

    public PacketUpdateBasin(BlockPos pos, ItemStack stack, List<Integer> aspects, int fillCount, long lastChangeTime) {
        this.pos = pos;
        this.stack = stack;
        this.lastChangeTime = lastChangeTime;
        this.aspectsSize = aspects.size();
        this.aspects = aspects;
        this.fillCount = fillCount;

    }

    public PacketUpdateBasin(TileEntityInfusionBasin te) {
        this(te.getPos(), te.inventory.getStackInSlot(0), te.aspectIDs, te.fillCount, te.lastChangeTime);
    }

    public PacketUpdateBasin() {
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        ByteBufUtils.writeItemStack(buf, stack);
        buf.writeLong(lastChangeTime);
        buf.writeInt(aspectsSize);
        for (int i : aspects) {
            buf.writeInt(i);
        }
        buf.writeInt(fillCount);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        stack = ByteBufUtils.readItemStack(buf);
        lastChangeTime = buf.readLong();
        aspectsSize = buf.readInt();
        aspects.clear();
        for (int i = 0; i < aspectsSize; i++) {

            aspects.add(buf.readInt());
        }
        fillCount = buf.readInt();

    }

    public static class Handler implements IMessageHandler<PacketUpdateBasin, IMessage> {

        @Override
        public IMessage onMessage(PacketUpdateBasin message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                TileEntityInfusionBasin te = (TileEntityInfusionBasin) Minecraft.getMinecraft().world.getTileEntity(message.pos);
                if (te != null) {
                    te.inventory.setStackInSlot(0, message.stack);
                    te.aspectIDs = message.aspects;
                    te.lastChangeTime = message.lastChangeTime;
                    te.fillCount = message.fillCount;
                }
            });
            return null;
        }

    }
}
